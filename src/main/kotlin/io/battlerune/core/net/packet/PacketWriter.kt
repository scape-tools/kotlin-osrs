package io.battlerune.core.net.packet

import io.battlerune.core.net.protocol.codec.game.GamePacket
import io.battlerune.game.world.actor.Player
import java.util.*

@FunctionalInterface
interface PacketWriter {

    fun writePacket(player: Player): Optional<GamePacket>

}