package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketType
import io.battlerune.net.packet.PacketEncoder
import java.util.*

class RootInterfaceOutgoingPacket(val interfaceId: Int) : PacketEncoder {

    override fun encode(player: Player): Optional<Packet> {
        val writer = RSByteBufWriter.alloc()
        writer.writeShort(interfaceId, ByteModification.ADD)
        return Optional.of(writer.toPacket(129, PacketType.FIXED))
    }

}