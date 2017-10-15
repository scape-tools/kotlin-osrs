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
        buffer.writeShort((msg.effect shl 8) or msg.color, ByteOrder.LE)
                .writeByte(pawn.rights.code)
                .writeByte(0, ByteModification.ADD)
                buffer.writeByte(msg.compressed.size + 1, ByteModification.NEG)
                buffer.buffer.writeBytes(msg.compressed, 0, msg.compressed.size)
    }

}