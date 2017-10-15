package io.battlerune.content

import com.google.common.eventbus.Subscribe
import io.battlerune.game.event.impl.ChatMessageEvent
import io.battlerune.game.world.actor.pawn.ChatMessage

class ChatMessageEventListener {

    @Subscribe
    fun onEvent(event: ChatMessageEvent) {
        if (!ChatMessage.isValid(event.msg, event.color, event.effect)) {
            return
        }

        event.player.chat(event.msg, ChatMessage.ChatColor.values()[event.color], ChatMessage.ChatEffect.values()[event.effect])
    }

}