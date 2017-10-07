package io.battlerune.net.packet

import io.battlerune.game.event.Event
import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.PacketReader

@FunctionalInterface
interface PacketDecoder<out E: Event> {

    fun decode(player: Player, reader: PacketReader) : E

}