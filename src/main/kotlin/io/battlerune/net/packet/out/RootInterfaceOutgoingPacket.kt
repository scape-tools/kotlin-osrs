package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.OutgoingPacket
import io.battlerune.net.packet.PacketType
import io.battlerune.net.packet.WritablePacket
import java.util.*

class RootInterfaceOutgoingPacket(val interfaceId: Int) : WritablePacket {

    override fun writePacket(player: Player): Optional<OutgoingPacket> {
        val writer = RSByteBufWriter.alloc()
        writer.writeShort(interfaceId, ByteModification.ADD)
        return Optional.of(writer.toOutgoingPacket(129, PacketType.FIXED))
    }

}