package io.battlerune.game.world.actor.pawn

import io.battlerune.game.world.World
import io.battlerune.net.codec.game.RSByteBufWriter

/**
 * This class represents the view of the player.
 *
 * @author nshusa
 */
class Viewport(val player: Player) {

    companion object {
        /**
         * The amount of tiles away a player can see
         */
        val VIEWING_DISTANCE = 14
    }

    /**
     * The players in this viewport
     */
    val playersInViewport = arrayOfNulls<Player>(World.MAX_PLAYER_COUNT)

    /**
     * The number of players within this players viewing distance
     */
    var playersInsideViewportCount = 0

    /**
     * The number of players outside of of this players viewing distance
     */
    var playersOutsideViewportCount = 0

    /**
     * The indexes of players within this players viewing distance
     */
    val playerIndexesInsideViewport = IntArray(World.MAX_PLAYER_COUNT)

    /**
     * The indexes of players outside of this players viewing distance
     */
    val playerIndexesOutsideViewport = IntArray(World.MAX_PLAYER_COUNT)

    /**
     * An array that flags an index within this array where a player should be skipped on the next process
     */
    val skipFlags = ByteArray(World.MAX_PLAYER_COUNT)

    var initialized = false

    /**
     * Initializes
     */
    fun initGPI(buffer: RSByteBufWriter) {
        playersInsideViewportCount = 0
        playersOutsideViewportCount = 0

        playersInViewport[player.index] = player
        playerIndexesInsideViewport[playersInsideViewportCount++] = player.index

        buffer.switchToBitAccess()
        buffer.writeBits(30, player.position.toPositionPacked())
        for (i in 1 until World.MAX_PLAYER_COUNT) {
            if (player.index != i) {
                buffer.writeBits(18, 0)
                playerIndexesOutsideViewport[playersOutsideViewportCount++] = i
            }
        }
        buffer.switchToByteAccess()
        initialized = true
    }

}