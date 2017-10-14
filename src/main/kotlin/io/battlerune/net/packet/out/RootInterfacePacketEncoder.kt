package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketType
import io.battlerune.net.packet.PacketEncoder

class RootInterfacePacketEncoder(private val interfaceId: Int): PacketEncoder {

    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc()
         writer.writeShort(interfaceId)
        return writer.toPacket(30, PacketType.FIXED)
    }

}