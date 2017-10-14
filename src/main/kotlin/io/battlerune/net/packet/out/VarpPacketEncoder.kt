package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class VarpPacketEncoder(val id: Int, val state: Int) : PacketEncoder {

    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc()

        if (state <= Byte.MIN_VALUE || state >= Byte.MAX_VALUE) { // varp large
            writer.writeShort(id)
            .writeInt(state, ByteOrder.ME)
            return writer.toPacket(9, PacketType.FIXED)
        } else { // small varp
            writer.writeByte(state, ByteModification.SUB)
            .writeShort(id, ByteModification.ADD, ByteOrder.LE)
            return writer.toPacket(185, PacketType.FIXED)
        }

    }

}