package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.OutgoingPacket
import io.battlerune.net.packet.PacketType
import io.battlerune.net.packet.WritablePacket
import java.util.*

class InterfaceTextOutgoingPacket(val root: Int, val child: Int, val message: String) : WritablePacket {

    override fun writePacket(player: Player): Optional<OutgoingPacket> {
        val writer = RSByteBufWriter.alloc()
        writer.writeString(message)
        writer.writeInt(root shl 16 or child, ByteOrder.IME)
        return Optional.of(writer.toOutgoingPacket(244, PacketType.VAR_SHORT))
    }

}