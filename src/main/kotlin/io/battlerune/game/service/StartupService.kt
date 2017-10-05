package io.battlerune.game.service

import org.apache.logging.log4j.LogManager
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

class StartupService {

    companion object {
        private val logger = LogManager.getLogger()

        private val service = Executors.newSingleThreadExecutor()

        private val queue: BlockingQueue<Runnable> = LinkedBlockingQueue()
    }

    fun start() {
        val tasks = queue.size
        while(queue.isNotEmpty()) {
            val task = queue.poll() ?: continue
            service.submit(task)
        }

        service.shutdown()

        logger.info("Loaded: $tasks startup tasks.")
    }

    fun queue(task: Runnable) : StartupService {
        queue.add(task)
        return this
    }

}