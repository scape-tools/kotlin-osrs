package io.battlerune.net.packet

import io.netty.buffer.ByteBuf

open class GamePacket(val opcode: Int, val type: PacketType, val payload: ByteBuf) {

    fun isPriotity() : Boolean {
        return false
    }

}