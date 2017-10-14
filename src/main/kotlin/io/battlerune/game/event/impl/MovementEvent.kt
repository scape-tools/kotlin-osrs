package io.battlerune.game.event.impl

import io.battlerune.game.event.Event
import io.battlerune.game.world.Position
import io.battlerune.game.world.actor.pawn.Player

class MovementEvent(val player: Player, val destination: Position) : Event