package io.battlerune.util

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled



object ByteBufUtil {

    fun readString(buf: ByteBuf):String {
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

    fun decryptXTEA(buffer: ByteBuf, key: IntArray): ByteBuf {
        val bytes = ByteArray(buffer.readableBytes())
        buffer.readBytes(bytes)
        val xteaBuffer = Unpooled.wrappedBuffer(bytes)
        decryptXTEA(xteaBuffer, 0, bytes.size, key)
        return xteaBuffer
    }

    private fun decryptXTEA(buffer: ByteBuf, start: Int, end: Int, key: IntArray) {
        if (key.size != 4) {
            throw IllegalArgumentException()
        }
        val numQuads = (end - start) / 8
        for (i in 0 until numQuads) {
            var sum = -0x61c88647 * 32
            var v0 = buffer.getInt(start + i * 8)
            var v1 = buffer.getInt(start + i * 8 + 4)
            for (j in 0..31) {
                v1 -= (v0 shl 4 xor v0.ushr(5)) + v0 xor sum + key[sum.ushr(11) and 3]
                sum -= -0x61c88647
                v0 -= (v1 shl 4 xor v1.ushr(5)) + v1 xor sum + key[sum and 3]
            }
            buffer.setInt(start + i * 8, v0)
            buffer.setInt(start + i * 8 + 4, v1)
        }
    }

}