package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.pawn.Player
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class InterfaceSettingPacketEncoder(val root: Int, val component: Int, val fromSlot: Int, val toSlot: Int, val setting: Int) : PacketEncoder {

    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc()
        writer.writeShort(fromSlot, ByteModification.ADD)
        writer.writeInt(setting, ByteOrder.IME)
        writer.writeInt((root shl 16) or component)
        writer.writeShort(toSlot, ByteModification.ADD)
        return writer.toPacket(72, PacketType.FIXED)
    }

}