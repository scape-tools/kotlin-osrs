package io.battlerune.game.world.actor.pawn.update

import io.battlerune.game.world.actor.pawn.Pawn
import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.player.block.PlayerAppearanceUpdateBlock
import io.battlerune.game.world.actor.pawn.player.block.PlayerGraphicUpdateBlock
import io.battlerune.net.codec.game.RSByteBufWriter

class UpdateBlockSet<P: Pawn> {

    companion object {
        val PLAYER_BLOCK_SET = UpdateBlockSet<Player>()

        init {
            PLAYER_BLOCK_SET.add(PlayerAppearanceUpdateBlock())
            PLAYER_BLOCK_SET.add(PlayerGraphicUpdateBlock())
        }

    }

    private val blocks = mutableSetOf<UpdateBlock<P>>()

    fun add(block: UpdateBlock<P>) {
        blocks.add(block)
    }

    fun encode(pawn: P, maskBuf: RSByteBufWriter) {
        var mask = 0

        blocks.forEach { block ->
            if (pawn.updateFlags.contains(block.flag)) {
                mask = mask or block.flag.mask
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