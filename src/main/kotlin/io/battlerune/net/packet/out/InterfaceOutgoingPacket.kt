package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketType
import io.battlerune.net.packet.PacketEncoder
import java.util.*

class InterfaceOutgoingPacket(val rootInterfaceId: Int, val childId: Int, val interfaceId: Int, val clickable: Boolean) : PacketEncoder {

    override fun encode(player: Player): Optional<Packet> {
        val writer = RSByteBufWriter.alloc()
        writer.writeByte(if (clickable) 1 else 0, ByteModification.NEG)
                .writeInt((rootInterfaceId shl 16) or childId, ByteOrder.ME)
                .writeShort(interfaceId, ByteModification.ADD)

        return Optional.of(writer.toPacket(111, PacketType.FIXED))
    }

}