package io.battlerune.net.packet

import io.battlerune.game.world.actor.Player
import java.util.*

@FunctionalInterface
interface WritablePacket {

    fun writePacket(player: Player): Optional<OutgoingPacket>

}