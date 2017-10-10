package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class DropClientPacketEncoder : PacketEncoder {

    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc()
        return writer.toPacket(28, PacketType.FIXED)
    }

}