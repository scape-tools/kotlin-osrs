package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class PlaySoundEffectPacketEncoder(val id: Int, val type: Int, val delay: Int) : PacketEncoder {

    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc()
        writer.writeShort(id)
        .writeByte(type)
        .writeShort(delay)
        return writer.toPacket(152, PacketType.FIXED)
    }

}