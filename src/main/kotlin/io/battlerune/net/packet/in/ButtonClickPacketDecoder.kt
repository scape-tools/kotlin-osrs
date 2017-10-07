package io.battlerune.net.packet.`in`

import io.battlerune.game.event.impl.ButtonClickEvent
import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.RSByteBufReader
import io.battlerune.net.packet.PacketDecoder

class ButtonClickPacketDecoder : PacketDecoder<ButtonClickEvent> {

    override fun decode(player: Player, reader: RSByteBufReader) : ButtonClickEvent {

        // 24772614 should be

        val value = reader.readInt()
        val interfaceId = value shr 16
        val buttonId = value and 0xFFFF

        val t = Integer.toBinaryString(value)
        val p = Integer.toBinaryString(24772614)

        println("value $t")
        println("actual $p")

        return ButtonClickEvent(player, interfaceId, buttonId)
    }

}