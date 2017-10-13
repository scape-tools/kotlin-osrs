package io.battlerune.net.packet.`in`

import io.battlerune.game.event.impl.InterfaceClickEvent
import io.battlerune.game.world.actor.pawn.Player
import io.battlerune.net.codec.game.RSByteBufReader
import io.battlerune.net.packet.PacketDecoder

class InterfaceClickPacketDecoder : PacketDecoder<InterfaceClickEvent> {

    override fun decode(player: Player, reader: RSByteBufReader): InterfaceClickEvent {
        val packed = reader.readUInt().toInt()
        val item = reader.readUShort()
        val slot = reader.readUShort()
        return InterfaceClickEvent(player, packed shr 16, packed and 0xFFFF, item, slot)
    }

}