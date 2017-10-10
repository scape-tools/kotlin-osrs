package io.battlerune.content

import com.google.common.eventbus.Subscribe
import io.battlerune.game.event.impl.CommandEvent

class CommandEventListener {

    @Subscribe
    fun onEvent(event: CommandEvent) {

        println("command event: ${event.command}")

    }

}