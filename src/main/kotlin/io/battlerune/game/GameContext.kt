package io.battlerune.game

import io.battlerune.game.service.GameService
import io.battlerune.game.service.StartupService
import io.battlerune.net.NetworkService

class GameContext {

    val startupService = StartupService()
    val gameService = GameService()
    val networkService = NetworkService()

}