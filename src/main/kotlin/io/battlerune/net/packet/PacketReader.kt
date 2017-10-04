package io.battlerune.net.packet

import io.battlerune.net.codec.game.GamePacket
import io.battlerune.game.world.actor.Player

@FunctionalInterface
interface PacketReader {

    fun readPacket(player: Player, packet: GamePacket)

}