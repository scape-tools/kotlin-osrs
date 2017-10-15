package io.battlerune.content

import com.google.common.eventbus.Subscribe
import io.battlerune.game.event.impl.ChatMessageEvent

class ChatMessageEventListener {

    @Subscribe
    fun onEvent(event: ChatMessageEvent) {
        if (event.color < 0 || event.effect < 0) {
            return
        }

        event.player.chat(event.msg, event.color, event.effect)
    }

}