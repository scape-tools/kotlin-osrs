package io.battlerune.content

import com.google.common.eventbus.Subscribe
import io.battlerune.game.event.impl.CommandEvent
import io.battlerune.game.world.Position

class CommandEventListener {

    @Subscribe
    fun onEvent(event: CommandEvent) {
        val player = event.player
        val parser = event.parser
        when(parser.cmd) {

            "test" -> {
                player.playGfx(90, 92, 0)
                player.playAnim(711, 0)
            }

            "gi" -> {
                player.client.showGroundItem(4151, 1, Position(player.position.x + 1, player.position.y))
            }

            "anim" -> {

                // e.g player.playAnim(866, 0) // dance emote

                if (parser.hasNext(2)) {
                    val id = parser.nextInt()
                    val delay = parser.nextInt()
                    player.playAnim(id, delay)
                } else if (parser.hasNext()) {
                    val id = parser.nextInt()
                    player.playAnim(id)
                }
            }

            "gfx" -> {

                // example gfx player.playGfx(90, 92, 0) // wind strike

                if (parser.hasNext(3)) {
                    val id = parser.nextInt()
                    val height = parser.nextInt()
                    val delay = parser.nextInt()
                    player.playGfx(id, height, delay)
                } else if (parser.hasNext(2)) {
                    val id = parser.nextInt()
                    val height = parser.nextInt()
                    player.playGfx(id, height)
                } else if (parser.hasNext()) {
                    val id = parser.nextInt()
                    player.playGfx(id)
                }
            }

            "fc" -> { // force chat

                if (parser.hasNext()) {
                    player.forceChat(parser.nextLine())
                }

            }

            "chat" -> {
                if (parser.hasNext()) {
                    player.chat(parser.nextLine())
                } else {
                    player.chat("Testing!!!")
                }

            }

        }

        println("command event: ${event.parser.cmd}")

    }

}