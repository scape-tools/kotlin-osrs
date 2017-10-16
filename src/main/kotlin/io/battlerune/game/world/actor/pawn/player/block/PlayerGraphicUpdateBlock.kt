package io.battlerune.game.world.actor.pawn.player.block

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.update.UpdateBlock
import io.battlerune.game.world.actor.pawn.update.UpdateFlag
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter

class PlayerGraphicUpdateBlock : UpdateBlock<Player>(0x800, UpdateFlag.GFX) {

    override fun encode(pawn: Player, buffer: RSByteBufWriter) {
        buffer.writeShort(pawn.graphic.id, ByteModification.ADD, ByteOrder.LE)
                .writeInt((pawn.graphic.height shl 16) or pawn.graphic.delay, ByteOrder.ME)
    }

}