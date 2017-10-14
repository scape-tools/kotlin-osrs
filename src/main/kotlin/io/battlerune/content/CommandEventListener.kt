package io.battlerune.content

import com.google.common.eventbus.Subscribe
import io.battlerune.game.event.impl.CommandEvent

class CommandEventListener {

    @Subscribe
    fun onEvent(event: CommandEvent) {
        val player = event.player
        val parser = event.parser
        when(parser.cmd) {

            "anim" -> {
                if (parser.hasNext(2)) {
                    val id = parser.nextInt()
                    val delay = parser.nextInt()

                    player.startAnim(id, delay)
                } else if (parser.hasNext()) {
                    val id = parser.nextInt()
                    player.startAnim(id)
                }
            }

            "gfx" -> {

                // example gfx player.startGfx(90, 92, 0) (wind strike)

                if (parser.hasNext(3)) {
                    val id = parser.nextInt()
                    val height = parser.nextInt()
                    val delay = parser.nextInt()
                    player.startGfx(id, height, delay)
                }
                if (parser.hasNext(2)) {
                    val id = parser.nextInt()
                    val height = parser.nextInt()
                    player.startGfx(id, height)
                } else if (parser.hasNext()) {
                    val id = parser.nextInt()
                    player.startGfx(id)
                }
            }

        }

        println("command event: ${event.parser.cmd}")

    }

}