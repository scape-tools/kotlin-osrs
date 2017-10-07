package io.battlerune.net.packet.`in`

import io.battlerune.game.event.impl.ButtonClickEvent
import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.PacketReader
import io.battlerune.net.packet.PacketDecoder

class ButtonClickPacketDecoder : PacketDecoder<ButtonClickEvent> {

    override fun decode(player: Player, reader: PacketReader) : ButtonClickEvent {
        val value = reader.readInt()
        val interfaceId = value shr 16
        val buttonId = value and 0xFFFF

        return ButtonClickEvent(interfaceId, buttonId)
    }

}