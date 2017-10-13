package io.battlerune.content

import com.google.common.eventbus.Subscribe
import io.battlerune.game.event.impl.ClientDimensionChangeEvent
import io.battlerune.game.widget.DisplayType

class ClientDimensionChangeEventListener {

    @Subscribe
    fun onEvent(event: ClientDimensionChangeEvent) {
        val player = event.player

        if (!player.inGame) {
            return
        }

        if (event.resized) {
//            if (player.displayType == DisplayType.FIXED) {
//                player.displayType = DisplayType.RESIZABLE
//                player.client.setRootInterface(player.displayType.root)
//                        .setInterfaceSets(548, 23, 161, 29)
//                        .setInterfaceSets(548, 20, 161, 13)
//                        .setInterfaceSets(548, 13, 161, 3)
//                        .setInterfaceSets(548, 15, 161, 6)
//                        .setInterfaceSets(548, 63, 161, 66)
//                        .setInterfaceSets(548, 65, 161, 68)
//                        .setInterfaceSets(548, 66, 161, 69)
//                        .setInterfaceSets(548, 67, 161, 70)
//                        .setInterfaceSets(548, 68, 161, 71)
//                        .setInterfaceSets(548, 69, 161, 72)
//                        .setInterfaceSets(548, 70, 161, 73)
//                        .setInterfaceSets(548, 71, 161, 74)
//                        .setInterfaceSets(548, 72, 161, 75)
//                        .setInterfaceSets(548, 73, 161, 76)
//                        .setInterfaceSets(548, 74, 161, 77)
//                        .setInterfaceSets(548, 75, 161, 78)
//                        .setInterfaceSets(548, 76, 161, 79)
//                        .setInterfaceSets(548, 77, 161, 80)
//                        .setInterfaceSets(548, 78, 161, 81)
//                        .setInterfaceSets(548, 14, 161, 4)
//                        .setInterfaceSets(548, 18, 161, 9)
//                        .setInterfaceSets(548, 10, 161, 28)
//                        .setInterfaceSets(548, 16, 161, 7)
//                        .setInterfaceSets(548, 17, 161, 8)
//                        .setInterfaceSets(548, 21, 161, 14)
//            }
        } else {
            if (player.displayType != DisplayType.FIXED) {
                player.displayType = DisplayType.FIXED
                player.client.setRootInterface(player.displayType.root)
                        .setInterfaceSets(161, 29, 548, 23)
                        .setInterfaceSets(161, 13, 548, 20)
                        .setInterfaceSets(161, 3, 548, 13)
                        .setInterfaceSets(161, 6, 548, 15)
                        .setInterfaceSets(161, 66, 548, 63)
                        .setInterfaceSets(161, 68, 548, 65)
                        .setInterfaceSets(161, 69, 548, 66)
                        .setInterfaceSets(161, 70, 548, 67)
                        .setInterfaceSets(161, 71, 548, 68)
                        .setInterfaceSets(161, 72, 548, 69)
                        .setInterfaceSets(161, 73, 548, 70)
                        .setInterfaceSets(161, 74, 548, 71)
                        .setInterfaceSets(161, 75, 548, 72)
                        .setInterfaceSets(161, 76, 548, 73)
                        .setInterfaceSets(161, 77, 548, 74)
                        .setInterfaceSets(161, 78, 548, 75)
                        .setInterfaceSets(161, 79, 548, 76)
                        .setInterfaceSets(161, 80, 548, 77)
                        .setInterfaceSets(161, 81, 548, 78)
                        .setInterfaceSets(161, 4, 548, 14)
                        .setInterfaceSets(161, 9, 548, 18)
                        .setInterfaceSets(161, 28, 548, 10)
                        .setInterfaceSets(161, 7, 548, 16)
                        .setInterfaceSets(161, 8, 548, 17)
                        .setInterfaceSets(161, 14, 548, 21)
            }
        }

        println("client dimension change event width ${event.width}, height ${event.height}, resized ${event.resized}")
    }

}