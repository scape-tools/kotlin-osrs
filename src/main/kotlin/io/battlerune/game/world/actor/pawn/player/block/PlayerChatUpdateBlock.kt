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
        buffer.writeShort((msg.color shl 8) or msg.effect, ByteOrder.LE)
        buffer.writeByte(pawn.rights.code)
        buffer.writeByte(0, ByteModification.ADD)
        buffer.writeByte(len + 1, ByteModification.NEG)
        buffer.writeSmart(msg.msg.length)

        println("length: ${msg.msg.length}")

        buffer.writeBytes(compressed, 0, len)

    }

}