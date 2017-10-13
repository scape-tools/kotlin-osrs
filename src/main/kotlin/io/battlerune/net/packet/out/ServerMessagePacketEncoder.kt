package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.pawn.Player
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class ServerMessagePacketEncoder(val message: String) : PacketEncoder {

    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc()
        writer.writeSmart(0) // must be more than 1 type
                .writeByte(0) // read another string if 1
                .writeString(message)
        return writer.toPacket(100, PacketType.VAR_BYTE)
    }

}