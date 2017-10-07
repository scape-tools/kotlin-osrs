package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketType
import io.battlerune.net.packet.PacketEncoder
import java.util.*

class InterfaceTextOutgoingPacket(val root: Int, val child: Int, val message: String) : PacketEncoder {

    override fun encode(player: Player): Optional<Packet> {
        val writer = RSByteBufWriter.alloc()
        writer.writeString(message)
        writer.writeInt((root shl 16) or child, ByteOrder.IME)
        return Optional.of(writer.toPacket(244, PacketType.VAR_SHORT))
    }

}