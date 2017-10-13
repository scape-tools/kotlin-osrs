package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.pawn.Player
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class ResetVarpPacketEncoder : PacketEncoder {

    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc()
        return writer.toPacket(182, PacketType.FIXED)
    }

}