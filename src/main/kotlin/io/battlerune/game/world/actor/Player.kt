package io.battlerune.game.world.actor

import io.battlerune.net.channel.PlayerChannel

class Player(val playerChannel: PlayerChannel) : Pawn() {

    lateinit var username: String
    lateinit var password: String

}