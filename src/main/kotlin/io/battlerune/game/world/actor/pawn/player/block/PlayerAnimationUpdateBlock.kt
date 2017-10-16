package io.battlerune.game.world.actor.pawn.player.block

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.update.UpdateBlock
import io.battlerune.game.world.actor.pawn.update.BlockType
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.RSByteBufWriter

class PlayerAnimationUpdateBlock : UpdateBlock<Player>(0x8, BlockType.ANIMATION) {

    override fun encode(pawn: Player, buffer: RSByteBufWriter) {
        buffer.writeShort(pawn.animation.id, ByteModification.ADD)
                .writeByte(pawn.animation.delay)
    }

}