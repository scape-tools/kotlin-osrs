package io.battlerune.net.packet

import io.battlerune.game.event.Event
import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.net.codec.game.RSByteBufReader

@FunctionalInterface
interface PacketDecoder<out E: Event> {

    fun decode(player: Player, reader: RSByteBufReader) : E?

}