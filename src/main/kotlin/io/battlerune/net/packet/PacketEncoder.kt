package io.battlerune.net.packet

import io.battlerune.game.world.actor.pawn.Player

@FunctionalInterface
interface PacketEncoder {

    fun encode(player: Player) : Packet

}