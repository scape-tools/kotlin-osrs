package io.battlerune.net.packet.`in`

import io.battlerune.game.world.actor.Player
import io.battlerune.net.packet.IncomingPacket
import io.battlerune.net.packet.ReadablePacket

class ButtonClickPacket : ReadablePacket {

    override fun readPacket(player: Player, packet: IncomingPacket) {

        val value = packet.getReader().readInt()
        val buttonId = value shr 16
        val interfaceId = value and 0xFFFF

        println("value1: $buttonId value2: $interfaceId")
    }

}