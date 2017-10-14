package io.battlerune.game.event.impl

import io.battlerune.game.event.Event
import io.battlerune.game.world.actor.pawn.player.Player

class ButtonClickEvent(val player: Player, val interfaceId: Int, val buttonId: Int) : Event