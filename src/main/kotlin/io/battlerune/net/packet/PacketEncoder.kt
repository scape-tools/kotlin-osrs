package io.battlerune.net.packet

import io.battlerune.game.world.actor.Player
import java.util.*

@FunctionalInterface
interface PacketEncoder {

    fun encode(player: Player): Optional<Packet>

}