package io.battlerune.game.event.impl

import io.battlerune.game.event.Event
import io.battlerune.game.world.actor.pawn.player.Player

class ChatMessageEvent(val player: Player, val msg: String, val color: Int, val effect: Int) : Event