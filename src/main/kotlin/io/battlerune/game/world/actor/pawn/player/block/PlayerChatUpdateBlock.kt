package io.battlerune.game.world.actor.pawn.player.block

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.update.PlayerUpdateBlock
import io.battlerune.game.world.actor.pawn.update.UpdateFlag
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.ByteOrder
import io.battlerune.net.codec.game.RSByteBufWriter

class PlayerChatUpdateBlock : PlayerUpdateBlock(UpdateFlag.CHAT) {

    override fun encode(pawn: Player, buffer: RSByteBufWriter) {

        buffer.writeShort((pawn.chatMessage.effect shl 8) or pawn.chatMessage.color, ByteOrder.LE)
                .writeByte(0, ByteModification.ADD)
                .writeByte(pawn.chatMessage.msg.length - 1, ByteModification.NEG)
                .writeBytes(pawn.chatMessage.msg.toByteArray())
    }

}