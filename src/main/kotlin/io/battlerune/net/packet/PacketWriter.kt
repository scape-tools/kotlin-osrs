package io.battlerune.net.packet

import io.battlerune.net.codec.game.GamePacket
import io.battlerune.game.world.actor.Player
import java.util.*

@FunctionalInterface
interface PacketWriter {

    fun writePacket(player: Player): Optional<GamePacket>

}