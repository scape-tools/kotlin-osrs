package io.battlerune.game.world.actor

import io.battlerune.game.world.Position
import io.battlerune.net.channel.PlayerChannel
import io.battlerune.net.packet.PacketWriter

class Player(val playerChannel: PlayerChannel) : Pawn(position) {

    companion object {
        val position = Position(0, 0)
    }

    lateinit var username: String
    lateinit var password: String

    fun writePacket(writer: PacketWriter) : Player {
        playerChannel.writeAndFlush(writer)
        return this
    }

}