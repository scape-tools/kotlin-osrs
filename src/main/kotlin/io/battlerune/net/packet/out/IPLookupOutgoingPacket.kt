package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.OutgoingPacket
import io.battlerune.net.packet.PacketType
import io.battlerune.net.packet.WritablePacket
import io.battlerune.util.IntUtils
import java.util.*

class IPLookupOutgoingPacket : WritablePacket {

    override fun writePacket(player: Player): Optional<OutgoingPacket> {
        val writer = RSByteBufWriter.alloc()

        println(player.playerChannel.hostAddress)

        writer.writeInt(IntUtils.ipToInt(player.playerChannel.hostAddress), ByteOrder.IME)
        return Optional.of(writer.toOutgoingPacket(210, PacketType.FIXED))
    }

}