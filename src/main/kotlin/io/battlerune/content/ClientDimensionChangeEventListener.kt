package io.battlerune.content

import com.google.common.eventbus.Subscribe
import io.battlerune.game.event.impl.ClientDimensionChangeEvent

class ClientDimensionChangeEventListener {

    @Subscribe
    fun onEvent(event: ClientDimensionChangeEvent) {
        println("client dimension change event width ${event.width}, height ${event.height}, resized ${event.resized}")
    }

}