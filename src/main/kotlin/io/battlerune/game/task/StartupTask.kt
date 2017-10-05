package io.battlerune.game.task

import org.apache.logging.log4j.LogManager

abstract class StartupTask<T>(t: Class<T>) : Runnable {

    val logger = LogManager.getLogger(t)

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