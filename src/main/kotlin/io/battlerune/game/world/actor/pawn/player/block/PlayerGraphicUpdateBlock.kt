package io.battlerune.game.world.actor.pawn.player.block

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.update.PlayerUpdateBlock
import io.battlerune.game.world.actor.pawn.update.UpdateFlag
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter

class PlayerGraphicUpdateBlock : PlayerUpdateBlock(UpdateFlag.GFX) {

    override fun encode(pawn: Player, buffer: RSByteBufWriter) {
        buffer.writeShort(pawn.graphic.id, ByteModification.ADD, ByteOrder.LE)
                .writeInt(pawn.graphic.delay, ByteOrder.ME)
    }

}