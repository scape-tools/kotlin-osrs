package io.battlerune.game.world.actor.pawn.player.block

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.update.PlayerUpdateBlock
import io.battlerune.game.world.actor.pawn.update.UpdateFlag
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter

class PlayerChatUpdateBlock : PlayerUpdateBlock(UpdateFlag.CHAT) {

    override fun encode(pawn: Player, buffer: RSByteBufWriter) {
        val msg = pawn.chatMessage

        val compressed = ByteArray(256)
        val len = pawn.context.huffman.compress(msg.msg, compressed)

        buffer.writeShort((msg.effect shl 8) or msg.color, ByteOrder.LE)
                .writeByte(1)
                .writeByte(2, ByteModification.ADD)

        println("size ${len + 1}")

                buffer.writeByte(3, ByteModification.SUB)
                buffer.buffer.writeBytes(compressed, 0, len)
    }

}