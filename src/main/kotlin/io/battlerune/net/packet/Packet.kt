package io.battlerune.net.packet

import io.netty.buffer.ByteBuf

class Packet(val opcode: Int, val packetType: PacketType, val payload: ByteBuf) {

    // TODO implement
    fun isPriority() : Boolean {
        return false
    }

}