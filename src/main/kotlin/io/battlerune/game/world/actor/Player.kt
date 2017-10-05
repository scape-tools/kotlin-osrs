package io.battlerune.game.world.actor

import io.battlerune.game.GameContext
import io.battlerune.net.channel.PlayerChannel
import io.battlerune.net.packet.PacketWriter

class Player(val playerChannel: PlayerChannel) : Pawn() {

    lateinit var username: String
    lateinit var password: String
    lateinit var context: GameContext

    fun init() {

    }

    fun onLogin() {

    }

    fun logout() {
        context.world.queueLogout(this)
    }

    fun onLogout() {

    }

    fun writePacket(writer: PacketWriter) : Player {
        playerChannel.writeAndFlush(writer)
        return this
    }

}