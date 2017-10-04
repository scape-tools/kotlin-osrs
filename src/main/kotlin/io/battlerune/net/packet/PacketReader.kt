package io.battlerune.net.packet

import io.battlerune.game.world.actor.Player

@FunctionalInterface
interface PacketReader {

    fun readPacket(player: Player, packet: GamePacket)

}