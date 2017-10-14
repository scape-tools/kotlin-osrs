package io.battlerune.content

import com.google.common.eventbus.Subscribe
import io.battlerune.game.event.impl.CommandEvent

class CommandEventListener {

    @Subscribe
    fun onEvent(event: CommandEvent) {
        val player = event.player
        val parser = event.parser
        when(parser.cmd) {

            "test" -> println("This works!")

            "anim" -> {

            }

            "gfx" -> {

                if (parser.hasNext(2)) {
                    val id = parser.nextInt()
                    val delay = parser.nextInt()
                    player.startGfx(id, delay)
                }

            }

        }

        println("command event: ${event.parser.cmd}")

    }

}