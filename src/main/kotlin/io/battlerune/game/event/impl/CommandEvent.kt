package io.battlerune.game.event.impl

import io.battlerune.game.event.Event
import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.player.cmd.CommandParser

class CommandEvent(val player: Player, val parser : CommandParser) : Event