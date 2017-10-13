package io.battlerune.game.event.impl

import io.battlerune.game.event.Event
import io.battlerune.game.world.actor.pawn.Player

class InterfaceClickEvent(val player: Player, val interfaceId: Int, val button: Int, val item: Int, val slot: Int) : Event