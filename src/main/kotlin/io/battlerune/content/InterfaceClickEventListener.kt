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
                    player.xpOverlay = !player.xpOverlay

                    if (player.xpOverlay) {
                        player.client.setInterface(548, 16, 122, true)
                        .setVarp(1055, 131072)
                    } else {
                        player.client.setVarp(1055, 0)
                                .removeInterface(35913744)
                    }

                } else if (button == 29) {
                    // minimap interface
                    //player.client.sendCS2Script(1749, arrayOf(50973940))
                    player.client.setInterface(548, 21, 595, true)
                            //.setInterfaceSettings(595, 17, 0, 4, 2)
                }

            }

            162 -> { if (event.button == 26) player.client.setInterface(548, 20, 553, false)}

            182 -> { if (event.button == 6) player.logout() }

            261 -> {
                if (event.button == 21) {
                    //player.client.sendCS2Script(917, arrayOf(-1, -1))
                            //player.client.setInterface(161, 13, 60, false)
                }
            }

            595 -> {
                if (event.button == 34) {
                    // click to remove minimap
                    player.client.removeInterface(35913749)
                }
            }

        }

    }

}