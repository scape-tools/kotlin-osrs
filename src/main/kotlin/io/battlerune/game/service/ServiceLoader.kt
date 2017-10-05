package io.battlerune.game.service

import io.battlerune.game.GameContext
import io.battlerune.io.FileSystemLoader
import io.battlerune.io.PacketRepositoryLoader
import io.battlerune.net.NetworkService

class ServiceLoader(gameContext: GameContext) {

    val startupService = StartupService()
    val gameService = GameService(gameContext)
    val networkService = NetworkService(gameContext)

    private fun queueStartupTasks() {
        startupService.queue(PacketRepositoryLoader())
                .queue(FileSystemLoader())
    }

    fun start() {
        queueStartupTasks()
        startupService.start()
        startupService.awaitUntilFinished()
        networkService.start(43594)
        gameService.startAsync()
    }

    // TODO implement eventually
    fun restart() {

    }

}