package io.battlerune.net.packet

import io.battlerune.net.codec.game.RSByteBufReader
import io.netty.buffer.ByteBuf

class IncomingPacket(val opcode: Int, val type: PacketType, private val payload: ByteBuf) {

    fun getReader() : RSByteBufReader {
        return RSByteBufReader.wrap(payload)
    }

    // TODO implement
    fun isPriority() : Boolean {
        return false
    }

}