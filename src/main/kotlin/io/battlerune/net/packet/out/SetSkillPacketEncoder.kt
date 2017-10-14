package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class SetSkillPacketEncoder(private val skill: Int, private val lvl: Int, private val xp: Int) : PacketEncoder {

    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc()
        .writeByte(lvl)
        .writeByte(skill)
        .writeInt(xp, ByteOrder.IME)
        return writer.toPacket(33, PacketType.FIXED)
    }

}