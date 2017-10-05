package io.battlerune.game.service

import io.battlerune.game.GameContext
import io.battlerune.io.FileSystemLoader
import io.battlerune.io.PacketRepositoryLoader
import io.battlerune.net.NetworkConstants
import io.battlerune.net.NetworkService

class ServiceLoader(gameContext: GameContext) {

    val startupService = StartupService()
    val gameService = GameService(gameContext)
    val networkService = NetworkService(gameContext)

    private fun processStartupTasks() {
        startupService.queue(PacketRepositoryLoader())
                .queue(FileSystemLoader())
    }

    fun start() {
        processStartupTasks()
        startupService.start()
        startupService.awaitUntilFinished()
        networkService.start(NetworkConstants.PORT)
        gameService.startAsync()
    }

    // TODO implement eventually
    fun restart() {

    }

}