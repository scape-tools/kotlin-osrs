package io.battlerune.net.codec.game

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.battlerune.net.codec.game.ByteModification.*
import io.battlerune.net.codec.game.ByteOrder.*
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketType

class RSByteBufWriter private constructor(val buffer: ByteBuf) {

    companion object {
        val BIT_MASK = listOf(0x0, 0x1, 0x3, 0x7, 0xf, 0x1f, 0x3f, 0x7f, 0xff, 0x1ff, 0x3ff, 0x7ff, 0xfff,
                0x1fff, 0x3fff, 0x7fff, 0xffff, 0x1ffff, 0x3ffff, 0x7ffff, 0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff,
                0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff, 0x1fffffff, 0x3fffffff, 0x7fffffff, -1)

        val DEFAULT_CAPACITY = 128

        fun wrap(buf: ByteBuf) : RSByteBufWriter {
            return RSByteBufWriter(buf)
        }

        fun alloc(size: Int = DEFAULT_CAPACITY) : RSByteBufWriter {
            return RSByteBufWriter(Unpooled.buffer(size))
        }
    }

    var accessType: AccessType = AccessType.BYTE

    var bitPos: Int = 0

    private fun setMode(accessType: AccessType) {
        if (this.accessType == accessType) {
            throw IllegalStateException("Already in $accessType mode.")
        }

        if (accessType == AccessType.BIT) {
            bitPos = buffer.writerIndex() * 8
        } else {
            buffer.writerIndex((bitPos + 7) / 8)
        }

        this.accessType = accessType
    }

    fun switchToBitAccess() : RSByteBufWriter {
        setMode(AccessType.BIT)
        return this
    }

    fun switchToByteAccess() : RSByteBufWriter {
        setMode(AccessType.BYTE)
        return this
    }

    fun writeFlag(flag: Boolean) : RSByteBufWriter {
        checkBitAccess()
        writeBits(1, if (flag) 1 else 0)
        return this
    }

    fun writeBit(value: Int) : RSByteBufWriter {
        checkBitAccess()
        writeBits(1, value)
        return this
    }

    private fun checkBitAccess() {
        if (accessType != AccessType.BIT) {
            throw IllegalStateException("Buffer must be in bit access!")
        }
    }

    private fun checkByteAccess() {
        if (accessType != AccessType.BYTE) {
            throw IllegalStateException("Buffer must be in byte access!")
        }
    }

    fun writeBits(amount:Int, value: Int): RSByteBufWriter {
        if (amount <= 0 || amount > 32) {
            throw IllegalArgumentException("Number of bits must be between 1 and 31 inclusive")
        }

        checkBitAccess()

        var numBits = amount

        var bytePos = bitPos shr 3
        buffer.writerIndex(bytePos + 1)

        if (buffer.writableBytes() < ((numBits + 7) / 8)) {
            buffer.capacity(buffer.capacity() * 2)
        }

        var bitOffset = 8 - (bitPos and 0x7)
        bitPos += numBits

        while (numBits > bitOffset) {
            var tmp = buffer.getByte(bytePos).toInt()
            tmp = tmp and BIT_MASK[bitOffset].inv()
            tmp = tmp or ((value shr (numBits - bitOffset)) and BIT_MASK[bitOffset])
            buffer.setByte(bytePos++, tmp)
            numBits -= bitOffset
            bitOffset = 8
        }

        if (numBits == bitOffset) {
            var tmp = buffer.getByte(bytePos).toInt()
            tmp = tmp and BIT_MASK[bitOffset].inv()
            tmp = tmp or (value and BIT_MASK[bitOffset])
            buffer.setByte(bytePos, tmp)
        } else {
            var tmp = buffer.getByte(bytePos).toInt()
            tmp = tmp and (BIT_MASK[numBits] shl (bitOffset - numBits)).inv()
            tmp = tmp or ((value and BIT_MASK[numBits]) shl (bitOffset - numBits))
            buffer.setByte(bytePos, tmp)
        }
        return this
    }

    fun writeByte(value: Int, modification: ByteModification = NONE) : RSByteBufWriter {
        checkByteAccess()
        var temp = value
        when(modification) {
            ADD -> { temp += 128 }
            NEG -> { temp = -temp }
            SUB -> { temp = 128 - value }
            NONE -> { }
        }
        buffer.writeByte(temp)
        return this
    }

    fun writeBytes(buf: ByteBuf) : RSByteBufWriter {
        checkByteAccess()
        buffer.writeBytes(buf)
        return this
    }

    fun writeBytes(writer: RSByteBufWriter) : RSByteBufWriter {
        checkByteAccess()
        buffer.writeBytes(writer.buffer)
        return this
    }

    fun writeBytes(bytes: ByteArray) : RSByteBufWriter {
        checkByteAccess()
        buffer.writeBytes(bytes)
        return this
    }

    fun writeBytes(buf: ByteBuf, mod: ByteModification) : RSByteBufWriter {
        for (i in 0 until buf.readableBytes()) {
            writeByte(buf.readByte().toInt(), mod)
        }
        return this
    }

    fun writeShort(value: Int, order: ByteOrder) : RSByteBufWriter {
        checkByteAccess()
         writeShort(value, NONE, order)
        return this
    }

    fun writeShort(value: Int, modification: ByteModification = NONE, order: ByteOrder = BE) : RSByteBufWriter {
        checkByteAccess()
        when(order) {
            BE -> {
                writeByte(value shr 8)
                writeByte(value, modification)
            }

            LE -> {
                writeByte(value, modification)
                writeByte(value shr 8)
            }
            else -> throw IllegalStateException("$order short is not possible!")
        }
        return this
    }

    fun writeInt(value: Int, order: ByteOrder = BE) : RSByteBufWriter {
        checkByteAccess()
        writeInt(value, NONE, order)
        return this
    }

    fun writeInt(value: Int, modification: ByteModification = NONE, order: ByteOrder = BE) : RSByteBufWriter {
        checkByteAccess()
        when(order) {
            BE -> {
                writeByte(value shr 24)
                writeByte(value shr 16)
                writeByte(value shr 8)
                writeByte(value, modification)
            }

            ME -> {
                writeByte(value shr 8)
                writeByte(value, modification)
                writeByte(value shr 24)
                writeByte(value shr 16)
            }

            IME -> {
                writeByte(value shr 16)
                writeByte(value shr 24)
                writeByte(value, modification)
                writeByte(value shr 8)
            }

            LE -> {
                writeByte(value, modification)
                writeByte(value shr 8)
                writeByte(value shr 16)
                writeByte(value shr 24)
            }
        }
        return this
    }

    fun writeLong(value: Long, modification: ByteModification = NONE, order: ByteOrder = BE) : RSByteBufWriter {
        checkByteAccess()
        when(order) {
            BE -> {
                writeByte((value shr 56).toInt())
                writeByte((value shr 48).toInt())
                writeByte((value shr 40).toInt())
                writeByte((value shr 32).toInt())
                writeByte((value shr 24).toInt())
                writeByte((value shr 16).toInt())
                writeByte((value shr 8).toInt())
                writeByte(value.toInt(), modification)
            }

            LE -> {
                writeByte(value.toInt(), modification)
                writeByte((value shr 8).toInt())
                writeByte((value shr 16).toInt())
                writeByte((value shr 24).toInt())
                writeByte((value shr 32).toInt())
                writeByte((value shr 40).toInt())
                writeByte((value shr 48).toInt())
                writeByte((value shr 56).toInt())
            }

            else -> throw UnsupportedOperationException("$order is not implemented!")
        }
        return this
    }

    fun writeString(string: String) : RSByteBufWriter {
        checkByteAccess()
        val array = string.toCharArray()
        for (ch in array) {
            buffer.writeByte(ch.toInt())
        }
        buffer.writeByte(0)
        return this
    }

    fun write24Int(value: Int) : RSByteBufWriter {
        checkByteAccess()
        buffer.writeMedium(value)
        return this
    }

    fun write24IntLE(value: Int) : RSByteBufWriter {
        checkByteAccess()
        buffer.writeMediumLE(value)
        return this
    }

    fun writeSmart(value: Int) : RSByteBufWriter {
        checkByteAccess()
        if (value < Byte.MAX_VALUE) {
            buffer.writeByte(value)
        } else {
            buffer.writeShort(value)
        }
        return this
    }

    fun toPacket(opcode : Int, packetType: PacketType = PacketType.FIXED) : Packet {
        checkByteAccess()
        return Packet(opcode, packetType, buffer)
    }

}