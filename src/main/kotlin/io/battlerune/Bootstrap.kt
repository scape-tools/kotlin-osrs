package io.battlerune

import io.battlerune.game.logic.GameService
import io.battlerune.game.logic.StartupService
import io.battlerune.io.PacketSizeParser
import io.battlerune.net.NetworkService

class Bootstrap {

    companion object {
        val startupService = StartupService()
        val gameService = GameService()
        val networkService = NetworkService()
    }

    private fun queueStartupTasks() {
        startupService.queue(PacketSizeParser())
    }

    fun start() {
        queueStartupTasks()
        startupService.start()
        gameService.startAsync()
        networkService.start(43594)
    }

}