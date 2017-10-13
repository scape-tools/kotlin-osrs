package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.pawn.Player
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class ShowGroundItemPacketEncoder(val item: Int, val amount: Int) : PacketEncoder {

    override fun encode(player: Player): Packet {
    val writer = RSByteBufWriter.alloc()
        writer.writeInt(item, ByteOrder.LE)
                .writeInt(amount, ByteOrder.LE)
                .writeInt(0, ByteOrder.LE)
        return writer.toPacket(20, PacketType.FIXED)
    }

}