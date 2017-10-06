package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.OutgoingPacket
import io.battlerune.net.packet.PacketType
import io.battlerune.net.packet.WritablePacket
import java.util.*

class InterfaceOutgoingPacket(val rootInterfaceId: Int, val childId: Int, val interfaceId: Int, val clickable: Boolean) : WritablePacket {

    override fun writePacket(player: Player): Optional<OutgoingPacket> {
        val writer = RSByteBufWriter.alloc()
        writer.writeByte(if (clickable) 1 else 0, ByteModification.NEG)
                .writeInt(rootInterfaceId and 16 or childId, ByteOrder.ME)
                .writeShort(interfaceId, ByteOrder.BE)
        return Optional.of(writer.toOutgoingPacket(191, PacketType.FIXED))
    }

}