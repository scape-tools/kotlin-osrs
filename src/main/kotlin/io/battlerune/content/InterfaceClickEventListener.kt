package io.battlerune.content

import com.google.common.eventbus.Subscribe
import io.battlerune.game.event.impl.InterfaceClickEvent

class InterfaceClickEventListener {

    @Subscribe
    fun onEvent(event: InterfaceClickEvent) {
        val player = event.player
        val button = event.button

        println("interface click event interface=${event.interfaceId} button=${event.button} item=${event.item} slot=${event.slot}")

        when(event.interfaceId) {

            // xp (used for testing atm)
            160 -> {

                if (button == 1) {

                }

            }

            182 -> { if (event.button == 6) player.logout() }

        }

    }

}