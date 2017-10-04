package io.battlerune.core.net.protocol.game

import io.netty.buffer.ByteBuf

class GamePacket(val opcode: Int, val type: PacketType, val payload: ByteBuf) {
}