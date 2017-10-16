package io.battlerune.game.world.actor.pawn.update

import com.google.common.collect.ImmutableSet
import io.battlerune.game.world.actor.pawn.Pawn
import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.player.block.*
import io.battlerune.net.codec.game.RSByteBufWriter

open class CachedUpdateBlock<in P: Pawn>(private val BLOCKS: ImmutableSet<UpdateBlock<P>>) {

    fun encode(pawn: P, maskBuf: RSByteBufWriter) {
        var mask = 0

        BLOCKS.forEach { BLOCK ->
            if (pawn.updateFlags.contains(BLOCK.type)) {
                mask = mask or BLOCK.mask
            }
        }

        if (mask >= 0x100) {
            mask = mask or 0x4
            maskBuf.writeByte(mask and 0xFF)
            maskBuf.writeByte(mask shr 8)
        } else {
            maskBuf.writeByte(mask and 0xFF)
        }

        BLOCKS.forEach { block ->
            if (pawn.updateFlags.contains(block.type)) {
                block.encode(pawn, maskBuf)
            }
        }

    }

}