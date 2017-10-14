package io.battlerune.game.world.actor.pawn.player.block

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.update.PlayerUpdateBlock
import io.battlerune.game.world.actor.pawn.update.UpdateFlag
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter

class PlayerChatUpdateBlock : PlayerUpdateBlock(UpdateFlag.CHAT) {

    override fun encode(pawn: Player, buffer: RSByteBufWriter) {
        val bytes = pawn.chatMessage.msg.toByteArray()
        buffer.writeShort((pawn.chatMessage.effect shl 8) or pawn.chatMessage.color, ByteOrder.LE)
                .writeByte(pawn.rights.code)
                .writeByte(0, ByteModification.ADD)
                .writeByte(bytes.size, ByteModification.NEG)
                .writeBytes(bytes)
    }

}