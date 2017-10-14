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
                if (parser.hasNext(2)) {
                    val id = parser.nextInt()
                    val delay = parser.nextInt()
                    player.startGfx(id, delay)
                } else if (parser.hasNext()) {
                    val id = parser.nextInt()
                    player.startGfx(id)
                }
            }

        }

        println("command event: ${event.parser.cmd}")

    }

}