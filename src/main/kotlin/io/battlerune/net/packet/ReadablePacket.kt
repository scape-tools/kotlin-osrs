package io.battlerune.net.packet

import io.battlerune.game.world.actor.Player

@FunctionalInterface
interface ReadablePacket {

    fun readPacket(player: Player, packet: IncomingPacket)

}