package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.pawn.Player
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class SetCameraPacketEncoder(val value1: Int, val value2: Int) : PacketEncoder {
    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc(2)
        writer.writeByte(value1)
        .writeByte(value2)
        return writer.toPacket(159, PacketType.FIXED)
    }
}