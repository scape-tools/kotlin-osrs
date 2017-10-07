package io.battlerune.game.event.impl

import io.battlerune.game.event.Event
import io.battlerune.game.world.actor.Player

class ClientDimensionChangeEvent(val player: Player, val width: Int, val height: Int, val resized: Boolean) : Event