package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketType
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.util.extensions.ipToInt

class IPLookupPacketEncoder(val hostAddress: String) : PacketEncoder {

    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc()
        writer.writeInt(hostAddress.ipToInt(), ByteOrder.IME)
        return writer.toPacket(210, PacketType.FIXED)
    }

}