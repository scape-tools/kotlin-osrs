package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class ItemOnInterfacePacketEncoder(private val widgetId: Int, private val childId: Int, private val itemId: Int, private val itemZoom: Int) : PacketEncoder {

    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc()
        writer.writeInt((widgetId shl 16) or childId, ByteOrder.LE)
                .writeShort(itemId, ByteOrder.LE)
                .writeInt(itemZoom, ByteOrder.LE)
        return writer.toPacket(20, PacketType.FIXED)
    }
}
