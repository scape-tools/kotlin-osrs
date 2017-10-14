package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class SetWeightPacketEncoder(val amount: Int) : PacketEncoder {

    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc()
        .writeShort(amount)
        return writer.toPacket(249, PacketType.FIXED)
    }

}