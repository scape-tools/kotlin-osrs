package io.battlerune.net.codec.game

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.battlerune.net.codec.game.ByteModification.*
import io.battlerune.net.codec.game.ByteOrder.*
import io.battlerune.net.packet.OutgoingPacket
import io.battlerune.net.packet.PacketType

class RSByteBufWriter() {

    companion object {
        val BIT_MASK = listOf(0x0, 0x1, 0x3, 0x7, 0xf, 0x1f, 0x3f, 0x7f, 0xff, 0x1ff, 0x3ff, 0x7ff, 0xfff,
                0x1fff, 0x3fff, 0x7fff, 0xffff, 0x1ffff, 0x3ffff, 0x7ffff, 0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff,
                0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff, 0x1fffffff, 0x3fffffff, 0x7fffffff, -1)

        val DEFAULT_CAPACITY = 128
    }

    val buffer: ByteBuf = Unpooled.buffer(DEFAULT_CAPACITY)

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

    fun writeBits(amount:Int = 1, value:Int): RSByteBufWriter {
        if (!buffer.hasArray()) {
            throw UnsupportedOperationException("This buffer must support an array for bit usage.")
        }
        var temp = amount
        val bytes = Math.ceil(temp / 8.0).toInt() + 1
        buffer.ensureWritable((bitPos + 7) / 8 + bytes)
        val buffer = this.buffer.array()
        var bytePos = bitPos shr 3
        var bitOffset = 8 - (bitPos and 7)
        bitPos += temp
        while (temp > bitOffset) {
            buffer[bytePos] = (buffer[bytePos].toInt() and BIT_MASK[bitOffset].inv()).toByte()
            buffer[bytePos++] = (buffer[bytePos++].toInt() or ((value shr (temp - bitOffset)) and BIT_MASK[bitOffset])).toByte()
            temp -= bitOffset
            bitOffset = 8
        }
        if (temp == bitOffset) {
            buffer[bytePos] = (buffer[bytePos].toInt() and BIT_MASK[bitOffset].inv()).toByte()
            buffer[bytePos] = (buffer[bytePos].toInt() or (temp and BIT_MASK[bitOffset])).toByte()
        } else {
            buffer[bytePos] = (buffer[bytePos].toInt() and (BIT_MASK[temp] shl (bitOffset - temp)).inv()).toByte()
            buffer[bytePos] = (buffer[bytePos].toInt() or ((temp and BIT_MASK[temp]) shl (bitOffset - temp))).toByte()
        }
        return this
    }

    fun writeByte(value: Int, modification: ByteModification = NONE) : RSByteBufWriter {
        var temp = value
        when(modification) {
            ADD -> {
                temp += 128
            }

            NEG -> {
                temp = -temp
            }

            SUB -> {
                temp = 128 - value
            }

            NONE -> {

            }

        }
        buffer.writeByte(temp)
        return this
    }

    fun writeBytes(buf: ByteBuf) : RSByteBufWriter {
        buffer.writeBytes(buf)
        return this
    }

    fun writeBytes(bytes: ByteArray) : RSByteBufWriter {
        buffer.writeBytes(bytes)
        return this
    }

    fun writeShort(value: Int, order: ByteOrder) {
        return writeShort(value, order)
    }

    fun writeShort(value: Int, modification: ByteModification = NONE, order: ByteOrder = BE) : RSByteBufWriter {
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

    fun writeInt(value: Int, modification: ByteModification = NONE, order: ByteOrder = BE) : RSByteBufWriter {
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

    fun toOutgoingPacket(opcode : Int, packetType: PacketType = PacketType.FIXED) : OutgoingPacket {
        if (accessType == AccessType.BIT) {
            throw IllegalStateException("Cannot be in bit access.")
        }
        
        return OutgoingPacket(opcode, packetType, buffer)
    }

}