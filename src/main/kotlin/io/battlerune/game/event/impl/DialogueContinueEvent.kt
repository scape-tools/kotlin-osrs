package io.battlerune.game.event.impl

import io.battlerune.game.event.Event
import io.battlerune.game.world.actor.pawn.player.Player

data class DialogueContinueEvent(val player: Player, val widgetHash: Int, val slot: Int) : Event