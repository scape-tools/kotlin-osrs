package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketType
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.util.IntUtils
import java.util.*

class IPLookupOutgoingPacket : PacketEncoder {

    override fun encode(player: Player): Optional<Packet> {
        val writer = RSByteBufWriter.alloc()

        println(player.playerChannel.hostAddress)

        writer.writeInt(IntUtils.ipToInt(player.playerChannel.hostAddress), ByteOrder.IME)
        return Optional.of(writer.toPacket(210, PacketType.FIXED))
    }

}