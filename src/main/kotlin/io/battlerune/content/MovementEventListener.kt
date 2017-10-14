package io.battlerune.content

import com.google.common.eventbus.Subscribe
import io.battlerune.game.event.impl.MovementEvent

class MovementEventListener {

    @Subscribe
    fun onEvent(event: MovementEvent) {
        println("clicked tile x=${event.destination.x} y=${event.destination.y}")
    }

}