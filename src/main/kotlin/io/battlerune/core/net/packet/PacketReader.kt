package io.battlerune.core.net.packet

import io.battlerune.core.net.protocol.codec.game.GamePacket
import io.battlerune.game.world.actor.Player

@FunctionalInterface
interface PacketReader {

    fun readPacket(player: Player, packet: GamePacket)

}