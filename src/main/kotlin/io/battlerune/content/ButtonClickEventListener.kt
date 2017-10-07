package io.battlerune.content

import com.google.common.eventbus.Subscribe
import io.battlerune.game.event.impl.ButtonClickEvent

class ButtonClickEventListener {

    @Subscribe
    fun onEvent(event: ButtonClickEvent) {

        println("interface: ${event.interfaceId} buttonId: ${event.buttonId}")

    }

}