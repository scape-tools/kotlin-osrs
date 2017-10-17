package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class ShowGroundItemPacketEncoder(val item: Int, val amount: Int) : PacketEncoder {

    override fun encode(player: Player): Packet {
    val writer = RSByteBufWriter.alloc()
        writer.writeShort(item, ByteModification.ADD)
                .writeShort(amount)
                .writeByte(0, ByteModification.ADD) // offset
        return writer.toPacket(187, PacketType.FIXED)
    }

}