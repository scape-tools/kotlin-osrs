package io.battlerune.net.packet.out

import io.battlerune.game.world.Position
import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class SetRegionCoordintePacketEncoder(val position: Position) : PacketEncoder {

    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc()
        writer.writeByte(position.regionX)
                .writeByte(position.regionY)
        return writer.toPacket(109, PacketType.FIXED)
    }

}