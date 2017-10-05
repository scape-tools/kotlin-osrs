package io.battlerune.game.world

import io.battlerune.game.GameContext
import io.battlerune.game.world.actor.Pawn
import io.battlerune.game.world.actor.PawnList
import io.battlerune.game.world.actor.Player
import java.util.concurrent.ConcurrentLinkedQueue

class World(val id: Int = 1, val gameContext: GameContext) {

    val logins = ConcurrentLinkedQueue<Player>()
    val logouts = ConcurrentLinkedQueue<Player>()
    val players = PawnList<Player>(1000)

    fun register(pawn: Pawn) {

    }

    fun unregister(pawn: Pawn) {

    }

}