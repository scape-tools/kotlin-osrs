package io.battlerune.game.task

import org.apache.logging.log4j.LogManager

abstract class StartupTask : Runnable {

    val logger = LogManager.getLogger()

    override fun run() {
        try {
            if (load()) {
                onComplete()
            }
        } catch (ex: Exception) {
            logger.warn("Startup task failed. ", ex)
        }
    }

    abstract fun load() : Boolean

    abstract fun onComplete()

}