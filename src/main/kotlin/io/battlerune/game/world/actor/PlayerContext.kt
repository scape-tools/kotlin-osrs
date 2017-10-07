package io.battlerune.game.world.actor

import io.battlerune.game.GameContext
import io.battlerune.net.channel.PlayerChannel

class PlayerContext(val channel: PlayerChannel, val username: String, val password: String, val gameContext: GameContext)