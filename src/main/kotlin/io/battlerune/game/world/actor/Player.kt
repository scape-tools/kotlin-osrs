package io.battlerune.game.world.actor

import io.battlerune.game.GameContext
import io.battlerune.net.channel.PlayerChannel

class Player(gameContext: GameContext, val username: String, val password: String) : Pawn() {

    lateinit var channel: PlayerChannel

}