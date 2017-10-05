package io.battlerune.game.service

import com.google.common.util.concurrent.AbstractScheduledService
import io.battlerune.game.GameContext
import java.util.concurrent.TimeUnit

class GameService(context: GameContext) : AbstractScheduledService() {

    companion object {
        val GAME_DELAY = 600.toLong()
        val TICK_RATE = 600.toLong()
    }

    override fun runOneIteration() {

        // dequeue logins

        // run tasks

        // client sync

        // dequeue logout

    }

    override fun scheduler(): Scheduler {
        return Scheduler.newFixedRateSchedule(GAME_DELAY, TICK_RATE, TimeUnit.MILLISECONDS)
    }

}