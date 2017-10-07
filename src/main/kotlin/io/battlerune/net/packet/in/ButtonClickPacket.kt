package io.battlerune.net.packet.`in`

import io.battlerune.game.event.impl.ButtonClickEvent
import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.PacketReader
import io.battlerune.net.packet.PacketDecoder

class ButtonClickPacket : PacketDecoder<ButtonClickEvent> {

    override fun decode(player: Player, reader: PacketReader) : ButtonClickEvent {

        val value = reader.readInt()
        val interfaceId = value shr 16
        val buttonId = value and 0xFFFF

        println("interfaceId: $interfaceId buttonId: $buttonId")

        return ButtonClickEvent(interfaceId, buttonId)
    }

}