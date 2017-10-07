package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class VarpPacketEncoder(val id: Int, val state: Int) : PacketEncoder {

    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc()

        if (state <= Byte.MIN_VALUE || state >= java.lang.Byte.MAX_VALUE) { // large varp
                writer.writeInt(state)
                        .writeShort(id)
            return writer.toPacket(217, PacketType.FIXED)
        } else { // small varp
            writer.writeShort(id, ByteModification.ADD, ByteOrder.LE)
                    .writeByte(state, ByteModification.NEG)
            return writer.toPacket(189, PacketType.FIXED)
        }

    }

}