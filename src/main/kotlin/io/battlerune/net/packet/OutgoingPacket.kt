package io.battlerune.net.packet

import io.netty.buffer.ByteBuf

open class OutgoingPacket(val opcode: Int, val type: PacketType, val payload: ByteBuf)