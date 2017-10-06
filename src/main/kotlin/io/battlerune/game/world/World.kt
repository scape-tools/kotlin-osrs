package io.battlerune.game.world

import io.battlerune.game.GameContext
import io.battlerune.game.world.actor.Pawn
import io.battlerune.game.world.actor.PawnList
import io.battlerune.game.world.actor.Player
import io.battlerune.net.NetworkConstants
import java.util.concurrent.ConcurrentLinkedQueue

class World(val id: Int = 1, val gameContext: GameContext) {

    val logins = ConcurrentLinkedQueue<Player>()
    val logouts = ConcurrentLinkedQueue<Player>()
    val players = PawnList<Player>(2048)

    fun queueLogin(player: Player) {
        logins.add(player)
    }

    fun queueLogout(player: Player) {
        logouts.add(player)
    }

    fun processLogins() {
        if (logins.isEmpty()) {
            return
        }

        var count = 0
        while(true) {

            if (count > NetworkConstants.LOGIN_LIMIT) {
                break
            }

            val player = logins.poll() ?: break

            register(player)

            count++
        }
    }

    fun processLogouts() {
        if (logouts.isEmpty()) {
            return
        }

        var count = 0
        while(true) {

            if (count > NetworkConstants.LOGOUT_LIMIT) {
                break
            }

            val player = logouts.poll()

            unregister(player)

            count++
        }
    }

    private fun register(pawn: Pawn) {
        if (pawn is Player) {
            players.add(pawn)
            pawn.init()
            pawn.onLogin()
        }
    }

    private fun unregister(pawn: Pawn) {
        if (pawn is Player) {
            pawn.onLogout()
            players.remove(pawn)
        }
    }

}