package io.battlerune.game.packet

import io.battlerune.core.net.protocol.codec.game.GamePacket
import io.battlerune.game.widget.world.actor.Player

@FunctionalInterface
interface Receivable {

    fun handlePacket(player: Player, packet: GamePacket)

}