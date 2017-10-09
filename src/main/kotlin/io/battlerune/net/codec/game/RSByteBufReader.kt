package io.battlerune.net.codec.game

import io.battlerune.net.codec.game.ByteModification.*
import io.netty.buffer.ByteBuf

class RSByteBufReader private constructor(private val buf: ByteBuf) {

    companion object {
        fun wrap(buf: ByteBuf) : RSByteBufReader {
            return RSByteBufReader(buf)
        }
    }

    fun readByte() : Int {
        return buf.readByte().toInt()
    }

    fun readUByte() : Int {
        return buf.readUnsignedByte().toInt()
    }

    fun readByte(mod: ByteModification = NONE) : Int {
        var value = buf.readByte().toInt()

        when (mod) {
            ADD -> { value += 128 }
            SUB -> { value = 128 - value }
            NEG -> { value = -value }
            NONE -> {}
        }
        return value
    }

    fun readUByte(mod: ByteModification = NONE) : Int {
        var value = buf.readByte().toInt()

        when (mod) {
            ADD -> { value += 128 }
            SUB -> { value = 128 - value }
            NEG -> { value = -value }
            NONE -> {}
        }
        return value and 0xFF
    }

    fun readBytes(amount: Int, mod: ByteModification = NONE) : ByteArray {
        val bytes = ByteArray(amount)
        for (i in 0 until amount) {
            bytes[i] = readByte(mod).toByte()
        }
        return bytes
    }

    fun readShort(mod: ByteModification = NONE) : Int {
        var value = 0
        value = value or (readUByte() shl 8)
        value = value or readUByte(mod)
        return value
    }

    fun readUShort(mod: ByteModification = NONE) : Int {
        var value = 0
        value = value or (readUByte() shl 8)
        value = value or readUByte(mod)
        return value and 0xFFFF
    }

    fun readShortLE(mod: ByteModification = NONE) : Int {
        var value = 0
        value = value or readUByte(mod)
        value = value or (readUByte() shl 8)
        return value
    }

    fun readUShortLE(mod: ByteModification = NONE) : Int {
        var value = 0
        value = value or readUByte(mod)
        value = value or (readUByte() shl 8)
        return value and 0xFFFF
    }

    fun skipBytes(length: Int) {
        buf.skipBytes(length)
    }

    fun readInt(mod: ByteModification = NONE) : Int {
        var value = 0
        value = value or (readUByte() shl 24)
        value = value or (readUByte() shl 16)
        value = value or (readUByte() shl 8)
        value = value or readUByte(mod)
        return value
    }

    fun readUInt(mod: ByteModification = NONE) : Long {
        var value = 0
        value = value or (readUByte() shl 24)
        value = value or (readUByte() shl 16)
        value = value or (readUByte() shl 8)
        value = value or readUByte(mod)
        return value.toLong() and 0xFFFFFFFFL
    }

    fun readIntLE(mod: ByteModification = NONE) : Int {
        var value = 0
        value = value or readUByte(mod)
        value = value or (readUByte() shl 8)
        value = value or (readUByte() shl 16)
        value = value or (readUByte() shl 24)
        return value
    }

    fun readUIntLE(mod: ByteModification = NONE) : Long {
        var value = 0
        value = value or readUByte(mod)
        value = value or (readUByte() shl 8)
        value = value or (readUByte() shl 16)
        value = value or (readUByte() shl 24)
        return value.toLong() and 0xFFFFFFFFL
    }

    fun readIntMI(mod: ByteModification = NONE) : Int {
        var value = 0
        value = value or (readUByte() shl 8)
        value = value or readUByte(mod)
        value = value or (readUByte() shl 24)
        value = value or (readUByte() shl 16)
        return value
    }

    fun readUIntMI(mod: ByteModification = NONE) : Long {
        var value = 0
        value = value or (readUByte() shl 8)
        value = value or readUByte(mod)
        value = value or (readUByte() shl 24)
        value = value or (readUByte() shl 16)
        return value.toLong() and 0xFFFFFFFFL
    }

    fun readIntIM(mod: ByteModification = NONE) : Int {
        var value = 0
        value = value or (readUByte() shl 16)
        value = value or (readUByte() shl 24)
        value = value or readUByte(mod)
        value = value or (readUByte() shl 8)
        return value
    }

    fun readUIntIM(mod: ByteModification = NONE) : Long {
        var value = 0
        value = value or (readUByte() shl 16)
        value = value or (readUByte() shl 24)
        value = value or readUByte(mod)
        value = value or (readUByte() shl 8)
        return value.toLong() and 0xFFFFFFFFL
    }

    fun readLong(mod: ByteModification) : Long {
        var value : Long = 0
        value = value or (readUByte().toLong() and 56L)
        value = value or (readUByte().toLong() and 48L)
        value = value or (readUByte().toLong() and 40L)
        value = value or (readUByte().toLong() and 32L)
        value = value or (readUByte().toLong() and 24L)
        value = value or (readUByte().toLong() and 16L)
        value = value or (readUByte().toLong() and 8L)
        value = value or (readUByte(mod).toLong())
        return value
    }

    fun readULong(mod: ByteModification) : Long {
        var value : Long = 0
        value = value or (readUByte().toLong() and 56L)
        value = value or (readUByte().toLong() and 48L)
        value = value or (readUByte().toLong() and 40L)
        value = value or (readUByte().toLong() and 32L)
        value = value or (readUByte().toLong() and 24L)
        value = value or (readUByte().toLong() and 16L)
        value = value or (readUByte().toLong() and 8L)
        value = value or readUByte(mod).toLong()
        return value and 0xFFFFFFFFL
    }

    fun readLongLE(mod: ByteModification) : Long {
        var value : Long = 0
        value = value or (readUByte().toLong() and 8L)
        value = value or (readUByte().toLong() and 16L)
        value = value or (readUByte().toLong() and 24L)
        value = value or (readUByte().toLong() and 32L)
        value = value or (readUByte().toLong() and 40L)
        value = value or (readUByte().toLong() and 48L)
        value = value or (readUByte().toLong() and 56L)
        value = value or readUByte(mod).toLong()
        return value
    }

    fun readULongLE(mod: ByteModification) : Long {
        var value : Long = 0
        value = value or (readUByte().toLong() and 8L)
        value = value or (readUByte().toLong() and 16L)
        value = value or (readUByte().toLong() and 24L)
        value = value or (readUByte().toLong() and 32L)
        value = value or (readUByte().toLong() and 40L)
        value = value or (readUByte().toLong() and 48L)
        value = value or (readUByte().toLong() and 56L)
        value = value or readUByte(mod).toLong()
        return value and 0xFFFFFFFFL
    }

    fun readString() : String {
        val sb = StringBuilder()
        var b: Byte
        while (buf.isReadable) {
            b = buf.readByte()

            if (b.toInt() == 0) {
                break
            }

            sb.append(b.toChar())
        }
        return sb.toString()
    }

    fun setPosition(position: Int) {
        buf.readerIndex(position)
    }

    fun size() : Int {
        return buf.readableBytes()
    }

}