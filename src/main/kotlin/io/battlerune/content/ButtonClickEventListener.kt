package io.battlerune.content

import com.google.common.eventbus.Subscribe
import io.battlerune.game.event.impl.ButtonClickEvent

class ButtonClickEventListener {

    @Subscribe
    fun onEvent(event: ButtonClickEvent) {
        val player = event.player

        println("interface: ${event.interfaceId} buttonId: ${event.buttonId}")

        when(event.interfaceId) {

            182 -> {
                if (event.buttonId == 6) {
                    player.logout()
                }
            }

            378 -> {
                if (event.buttonId == 6) {
                    player.inGame = true
                    player.client
                            .setRootInterface(player.displayType.root)
                            .setInterfaceSets(165, 1, 548, 23)
                            .setInterfaceSets(165, 2, 548, 13)
                            .setInterfaceSets(165, 3, 548, 15)
                            .setInterfaceSets(165, 4, 548, 16)
                            .setInterfaceSets(165, 5, 548, 17)
                            .setInterfaceSets(165, 6, 548, 20)
                            .setInterfaceSets(165, 7, 548, 63)
                            .setInterfaceSets(165, 8, 548, 65)
                            .setInterfaceSets(165, 9, 548, 66)
                            .setInterfaceSets(165, 10, 548, 67)
                            .setInterfaceSets(165, 11, 548, 68)
                            .setInterfaceSets(165, 12, 548, 69)
                            .setInterfaceSets(165, 13, 548, 70)
                            .setInterfaceSets(165, 14, 548, 71)
                            .setInterfaceSets(165, 15, 548, 72)
                            .setInterfaceSets(165, 16, 548, 73)
                            .setInterfaceSets(165, 17, 548, 74)
                            .setInterfaceSets(165, 18, 548, 75)
                            .setInterfaceSets(165, 19, 548, 76)
                            .setInterfaceSets(165, 20, 548, 77)
                            .setInterfaceSets(165, 21, 548, 78)
                            .setInterfaceSets(165, 22, 548, 14)
                            .setInterfaceSets(165, 23, 548, 18)
                            .setInterfaceSets(165, 24, 548, 10)
                            .setInterfaceSets(165, 30, 548, 21)
                            //.setVarp(1055, 132608)

                }
            }

        }

    }

}