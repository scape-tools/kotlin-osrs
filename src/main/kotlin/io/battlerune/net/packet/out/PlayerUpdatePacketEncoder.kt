package io.battlerune.net.packet.out

import io.battlerune.game.world.World
import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.player.Viewport
import io.battlerune.game.world.actor.pawn.update.CachedUpdateBlock
import io.battlerune.game.world.actor.pawn.update.PlayerCachedUpdateBlock
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class PlayerUpdatePacketEncoder : PacketEncoder {
    lateinit var player: Player

    var playersAdded = 0
    var skip = 0

    override fun encode(player: Player): Packet {
        this.player = player

        val packetBuffer = RSByteBufWriter.alloc()
        val maskBuffer = RSByteBufWriter.alloc()
        processPlayersInViewport(packetBuffer, maskBuffer, true)
        processPlayersInViewport(packetBuffer, maskBuffer, false)
        processPlayersOutsideViewport(packetBuffer, maskBuffer, false)
        processPlayersOutsideViewport(packetBuffer, maskBuffer, true)

        player.viewport.playersInsideViewportCount = 0
        player.viewport.playersOutsideViewportCount = 0

        for (index in 1 until World.MAX_PLAYER_COUNT) {
            player.viewport.skipFlags[index] = (player.viewport.skipFlags[index].toInt() shr 1).toByte()

            val globalPlayer = player.context.world.players.get(index)

            if (globalPlayer != null) {
                player.viewport.playerIndexesInsideViewport[player.viewport.playersInsideViewportCount++] = index
            } else {
                player.viewport.playerIndexesOutsideViewport[player.viewport.playersOutsideViewportCount++] = index
            }
        }

        packetBuffer.writeBytes(maskBuffer.buffer)
        return packetBuffer.toPacket(83, PacketType.VAR_SHORT)
    }

    private fun processPlayersInViewport(buffer: RSByteBufWriter, maskBuffer: RSByteBufWriter, evenIndex: Boolean) {
        buffer.switchToBitAccess()
        skip = 0
        playersAdded = 0
        for (currentIndex in 0 until player.viewport.playersInsideViewportCount) {
            val localPlayerIndex = player.viewport.playerIndexesInsideViewport[currentIndex]

            // skip player indexes that are either even or odd.
            if (if(evenIndex) player.viewport.skipFlags[localPlayerIndex].toInt() and 0x1 != 0 else player.viewport.skipFlags[localPlayerIndex].toInt() and 0x1 == 0 ) {
                continue
            }

            if (skip > 0) {
                --skip
                player.viewport.skipFlags[localPlayerIndex] = (player.viewport.skipFlags[localPlayerIndex].toInt() or 0x2).toByte()
                continue
            }

            val localPlayer = player.viewport.playersInViewport[localPlayerIndex]

            val updateRequired = localPlayer != null && localPlayer.updateFlags.isNotEmpty()

            // 1. check to see if the local player needs and flag-based updates
            buffer.writeFlag(updateRequired)

            if (!updateRequired) {
                // there is no update for this player so we are going to skip them and see if we can skip more players
                skipPlayers(buffer, currentIndex, evenIndex)

                // flag this index as being skipped
                player.viewport.skipFlags[localPlayerIndex] = (player.viewport.skipFlags[localPlayerIndex].toInt() or 0x2).toByte()
            } else {
                // now that an update is required we need to know what type of update to perform
                updatePlayerInViewport(buffer, maskBuffer, localPlayer!!)
            }

        }
        buffer.switchToByteAccess()
    }

    private fun updatePlayerInViewport(buffer: RSByteBufWriter, maskBuffer: RSByteBufWriter, localPlayer: Player) {
        val flagUpdateRequired = localPlayer.updateFlags.isNotEmpty()
        buffer.writeFlag(flagUpdateRequired)

        if (flagUpdateRequired) {
            PlayerCachedUpdateBlock.CACHED_UPDATE_BLOCK.encode(localPlayer, maskBuffer)
        }

        // TODO support for walking type 1, running type 2 and teleporting type 3
        buffer.writeBits(2, 0)
    }

    private fun skipPlayers(buffer: RSByteBufWriter, currentIndex: Int, evenIndex: Boolean) {

        // first loop through the next indexes until a player can't be skipped or we reached the end of the list
        for (nextIndex in (currentIndex + 1) until player.viewport.playersInsideViewportCount) {
            val nextPlayerIndex = player.viewport.playerIndexesInsideViewport[nextIndex]

            // skip player indexes that are either even or odd.
            if (if(evenIndex) player.viewport.skipFlags[nextPlayerIndex].toInt() and 0x1 != 0 else player.viewport.skipFlags[nextPlayerIndex].toInt() and 0x1 == 0 ) {
                continue
            }

            // we are gonna grab the next player and determine if they should be skipped
            val nextPlayer = player.viewport.playersInViewport[nextPlayerIndex]

            val requiresFlagUpdates = nextPlayer != null && nextPlayer.updateFlags.isNotEmpty()

            // check the next player requires updates, if the player does then we can't skip them.
            if (requiresFlagUpdates) {
                break
            }

            skip++
        }

        // after we determine how many players we can skip we need to tell the client
        writeSkip(buffer, skip)

    }

    private fun processPlayersOutsideViewport(buffer: RSByteBufWriter, updateBuffer: RSByteBufWriter, evenIndex: Boolean) {
        buffer.switchToBitAccess()
        skip = 0
        playersAdded = 0

        for (currentIndex in 0 until player.viewport.playersOutsideViewportCount) {
            val globalPlayerIndex = player.viewport.playerIndexesOutsideViewport[currentIndex]

            // skip player indexes that are either even or odd.
            if (if (evenIndex) player.viewport.skipFlags[globalPlayerIndex].toInt() and 0x1 != 0 else player.viewport.skipFlags[globalPlayerIndex].toInt() and 0x1 == 0) {
                continue
            }

            // get the global player
            val globalPlayer = player.context.world.players.get(globalPlayerIndex)

            /*
            Now we need to figure out what needs to be done, we should first decide if this player needs to be updated.

            A player should be updated if they are not skipped.

            A player should be skipped if...

            1. They do not exist
            2. They are not within viewing distance from us.
            3. The player is the this player sending the packet
             */

            val updateRequired = !(globalPlayer == null || !globalPlayer.position.withinDistance(player.position, Viewport.VIEWING_DISTANCE) || globalPlayer.index == player.index)

            buffer.writeFlag(updateRequired)

            if (!updateRequired) { // skip the player
                skipPlayers(buffer, currentIndex, evenIndex)
                player.viewport.skipFlags[globalPlayerIndex] = (player.viewport.skipFlags[globalPlayerIndex].toInt() or 0x2).toByte()
                continue
            }

            // perform some type of update on the player
            if (updatePlayerOutsideViewport(buffer, globalPlayer!!)) {
                // only if the player was updated should we process them next time around
                player.viewport.skipFlags[globalPlayerIndex] = (player.viewport.skipFlags[globalPlayerIndex].toInt() or 0x2).toByte()
            }

        }

        buffer.switchToByteAccess()
    }

    private fun updatePlayerOutsideViewport(buffer: RSByteBufWriter, globalPlayer: Player) : Boolean {

        return false
    }

    private fun writeSkip(buffer: RSByteBufWriter, skipCount: Int) {
        assert(skipCount >= 0)
        when {
            skipCount == 0 -> buffer.writeBits(2, 0)
            skipCount < 32 -> {
                buffer.writeBits(2, 1)
                buffer.writeBits(5, skipCount)
            }
            skipCount < 256 -> {
                buffer.writeBits(2, 2)
                buffer.writeBits(8, skipCount)
            }
            skipCount < 2048 -> {
                buffer.writeBits(2, 3)
                buffer.writeBits(11, skipCount)
            }
        }
    }

}