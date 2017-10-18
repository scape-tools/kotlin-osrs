package io.battlerune.content

import com.google.common.eventbus.Subscribe
import io.battlerune.game.event.impl.DialogueContinueEvent

class DialogueContinueEventListener {

    @Subscribe
    fun onEvent(event: DialogueContinueEvent) {
        val widget = event.widgetHash shr 16
        val child  = event.widgetHash and 0xFFFF

        println("Dialogue continue event: [widget=$widget, child=$child, slot=${event.slot}]")

        when (widget) {
            193 -> {
                when (child) {
                    2 -> {
                        val hash = (162 shl 16) or 546
                        event.player.client.removeInterface(hash)
                    }
                }
            }
        }
    }
}