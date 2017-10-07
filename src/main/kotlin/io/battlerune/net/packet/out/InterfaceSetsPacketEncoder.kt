package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class InterfaceSetsPacketEncoder(val fromRoot: Int, val fromChild: Int, val toRoot: Int, val toChild : Int) : PacketEncoder {

    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc()
        writer.writeInt((toRoot shl 16) or toChild, ByteOrder.LE)
                .writeInt((fromRoot shl 16) or fromChild, ByteOrder.LE)
        return writer.toPacket(78, PacketType.FIXED)
    }

}