package io.battlerune.game.packet

import io.battlerune.core.net.protocol.codec.game.GamePacket
import io.battlerune.game.widget.world.actor.Player
import java.util.*

@FunctionalInterface
interface Sendable {

    fun writePacket(player: Player): Optional<GamePacket>

}