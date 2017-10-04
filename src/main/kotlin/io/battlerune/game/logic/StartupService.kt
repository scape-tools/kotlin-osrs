package io.battlerune.game.logic

import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

class StartupService {

    private val service = Executors.newSingleThreadExecutor()

    private val queue: BlockingQueue<Runnable> = LinkedBlockingQueue()

    fun start() {
        val tasks = queue.size
        while(queue.isNotEmpty()) {
            val task = queue.poll() ?: continue
            service.submit(task)
        }
        println("Loaded: $tasks startup tasks.")
    }

    fun queue(task: Runnable) : StartupService {
        queue.add(task)
        return this
    }

    fun stop() {
        service.shutdown()
    }

}