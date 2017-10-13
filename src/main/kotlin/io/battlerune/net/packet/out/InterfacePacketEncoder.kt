package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.pawn.Player
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketType
import io.battlerune.net.packet.PacketEncoder

class InterfacePacketEncoder(private val rootInterfaceId: Int, private val childId: Int, private val interfaceId: Int, private val clickable: Boolean) : PacketEncoder {

    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc()
        writer.writeByte(if (clickable) 1 else 0, ByteModification.SUB)
                .writeShort(interfaceId, ByteOrder.LE)
                .writeInt((rootInterfaceId shl 16) or childId, ByteOrder.IME)
        return writer.toPacket(127, PacketType.FIXED)
    }

}