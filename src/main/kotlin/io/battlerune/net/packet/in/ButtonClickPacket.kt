package io.battlerune.net.packet.`in`

import io.battlerune.game.world.actor.Player
import io.battlerune.net.packet.IncomingPacket
import io.battlerune.net.packet.ReadablePacket

class ButtonClickPacket : ReadablePacket {

    override fun readPacket(player: Player, packet: IncomingPacket) {

        val value = packet.getReader().readInt()
        val interfaceId = value shr 16
        val buttonId = value and 0xFFFF

        println("interfaceId: $interfaceId buttonId: $buttonId")
    }

}