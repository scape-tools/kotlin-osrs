package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.OutgoingPacket
import io.battlerune.net.packet.PacketType
import io.battlerune.net.packet.WritablePacket
import java.util.*

class RootInterfaceOutgoingPacket(val interfaceId: Int) : WritablePacket {

    override fun writePacket(player: Player): Optional<OutgoingPacket> {
        val writer = RSByteBufWriter.alloc()
        writer.writeShort(interfaceId, ByteOrder.LE)
        return Optional.of(writer.toOutgoingPacket(90, PacketType.FIXED))
    }

}