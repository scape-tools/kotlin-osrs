package io.battlerune.game.world

import com.google.common.eventbus.EventBus
import io.battlerune.content.ButtonClickEventListener
import io.battlerune.content.ClientDimensionChangeEventListener
import io.battlerune.game.GameContext
import io.battlerune.game.world.actor.Pawn
import io.battlerune.game.world.actor.PawnList
import io.battlerune.game.world.actor.Player
import io.battlerune.net.NetworkConstants
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.concurrent.ConcurrentLinkedQueue

class World(val gameContext: GameContext) {

    companion object {
        val MAX_PLAYER_COUNT = 2048
    }

    val eventBus = EventBus()
    val logger: Logger = LogManager.getLogger()

    init {
        init()
    }

    val logins = ConcurrentLinkedQueue<Player>()
    val logouts = ConcurrentLinkedQueue<Player>()
    val players = PawnList<Player>(MAX_PLAYER_COUNT)

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

            player.onLogout()

            unregister(player)

            count++
        }
    }

    fun processIncomingPackets() {
        if (players.isEmpty()) {
            return
        }

        //println("processing incoming packets playercount ${players.size()}")

        for (player in players.list) {

            player ?: continue

            player.channel.handleQueuedPackets()
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
            println("logins ${logins.size} logouts ${logouts.size} size ${players.size()}")
        }
    }

    private fun init() {
        registerEvents()
    }

    private fun registerEvents() {
        eventBus.register(ButtonClickEventListener())
        eventBus.register(ClientDimensionChangeEventListener())
        logger.info("Registered event listeners")
     }

}