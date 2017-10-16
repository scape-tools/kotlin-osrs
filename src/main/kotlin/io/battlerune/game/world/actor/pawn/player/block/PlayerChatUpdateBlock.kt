package io.battlerune.game.world.actor.pawn.player.block

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.update.UpdateBlock
import io.battlerune.game.world.actor.pawn.update.UpdateFlag
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter

class PlayerChatUpdateBlock : UpdateBlock<Player>(0x10, UpdateFlag.CHAT) {

    override fun encode(pawn: Player, buffer: RSByteBufWriter) {
        val msg = pawn.chatMessage

        val compressed = ByteArray(256)
        val len = pawn.context.huffman.compress(msg.msg, compressed)
        buffer.writeShort((msg.color.code shl 8) or msg.effect.code, ByteOrder.LE)
        buffer.writeByte(pawn.rights.code)
        buffer.writeByte(0, ByteModification.ADD)
        buffer.writeByte(len + 1, ByteModification.NEG)
        buffer.writeBytesReverse(compressed, len)
        buffer.writeSmart(msg.msg.length)
    }

}