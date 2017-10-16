package io.battlerune.game.world.actor.pawn.update

import io.battlerune.game.world.actor.pawn.Pawn
import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.player.block.*
import io.battlerune.net.codec.game.RSByteBufWriter

class CachedUpdateBlock<P: Pawn> {

    companion object {
        val PLAYER_CACHED_BLOCKS = CachedUpdateBlock<Player>()

        init {
            PLAYER_CACHED_BLOCKS.add(PlayerFacePawnUpdateBlock())
            PLAYER_CACHED_BLOCKS.add(PlayerGraphicUpdateBlock())
            PLAYER_CACHED_BLOCKS.add(PlayerAppearanceUpdateBlock())
            PLAYER_CACHED_BLOCKS.add(PlayerTemporaryMovementUpdateBlock())
            PLAYER_CACHED_BLOCKS.add(PlayerCacheMovementTypeUpdateBlock())
            PLAYER_CACHED_BLOCKS.add(PlayerContextMenuUpdateBlock())
            PLAYER_CACHED_BLOCKS.add(PlayerForceMovementUpdateBlock())
            PLAYER_CACHED_BLOCKS.add(PlayerHitUpdateBlock())
            PLAYER_CACHED_BLOCKS.add(PlayerAnimationUpdateBlock())
            PLAYER_CACHED_BLOCKS.add(PlayerChatUpdateBlock())
            PLAYER_CACHED_BLOCKS.add(PlayerFaceCoordinateUpdateBlock())
            PLAYER_CACHED_BLOCKS.add(PlayerForceChatUpdateBlock())
        }

    }

    private val blocks = linkedSetOf<UpdateBlock<P>>()

    fun add(block: UpdateBlock<P>) {
        blocks.add(block)
    }

    fun encode(pawn: P, maskBuf: RSByteBufWriter) {
        var mask = 0

        blocks.forEach { block ->
            if (pawn.updateFlags.contains(block.flag)) {
                mask = mask or block.mask
            }
        }

        if (mask >= 0x100) {
            mask = mask or 0x4
            maskBuf.writeByte(mask and 0xFF)
            maskBuf.writeByte(mask shr 8)
        } else {
            maskBuf.writeByte(mask and 0xFF)
        }

        blocks.forEach { block ->
            if (pawn.updateFlags.contains(block.flag)) {
                block.encode(pawn, maskBuf)
            }
        }

    }

}