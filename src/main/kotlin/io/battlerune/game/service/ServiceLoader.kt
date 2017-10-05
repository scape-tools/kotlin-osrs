package io.battlerune.game.service

import io.battlerune.game.GameContext
import io.battlerune.io.PacketSizeParser

class ServiceLoader {

    companion object {
        val gameContext = GameContext()
    }

    private fun queueStartupTasks() {
        gameContext.startupService.queue(PacketSizeParser())
    }

    fun start() {
        queueStartupTasks()
        gameContext.startupService.start()
        gameContext.gameService.startAsync()
        gameContext.networkService.start(43594)
    }

}