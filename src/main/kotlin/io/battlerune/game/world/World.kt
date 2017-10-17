package io.battlerune.game.world

import com.google.common.eventbus.EventBus
import io.battlerune.content.*
import io.battlerune.game.GameContext
import io.battlerune.game.world.actor.pawn.Pawn
import io.battlerune.game.world.actor.pawn.PawnList
import io.battlerune.game.world.actor.pawn.player.Player
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

    fun updatePlayers() {
        for (player in players.list) {
            player?.preUpdate()
            player?.update()
            player?.postUpdate()
        }
    }

    fun processLogins() {
        if (logins.isEmpty()) {
            return
        }

        for (i in 0 until NetworkConstants.LOGIN_LIMIT) {
            val player = logins.poll() ?: break
            register(player)
        }

    }

    fun processLogouts() {
        if (logouts.isEmpty()) {
            return
        }

        for (i in 0 until NetworkConstants.LOGOUT_LIMIT) {
            val player = logouts.poll() ?: break
            player.onLogout()
            unregister(player)
        }
    }

    private fun register(pawn: Pawn) {
        if (pawn is Player) {
            players.add(pawn)
            pawn.initialized = true
            pawn.teleported = true
            pawn.onLogin()
        }
    }

    private fun unregister(pawn: Pawn) {
        if (pawn is Player) {
            pawn.onLogout()
            players.remove(pawn)
        }
    }

    private fun init() {
        registerEvents()
    }

    private fun registerEvents() {
        eventBus.register(ButtonClickEventListener())
        eventBus.register(ClientDimensionChangeEventListener())
        eventBus.register(InterfaceClickEventListener())
        eventBus.register(CommandEventListener())
        eventBus.register(RegionChangeEventListener())
        eventBus.register(MovementEventListener())
        eventBus.register(ChatMessageEventListener())
        eventBus.register(DialogueContinueEventListener())
        logger.info("Registered event listeners")
     }

}