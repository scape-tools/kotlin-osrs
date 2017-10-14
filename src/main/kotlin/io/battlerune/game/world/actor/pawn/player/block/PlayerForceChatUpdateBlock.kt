package io.battlerune.game.world.actor.pawn.player.block

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.update.PlayerUpdateBlock
import io.battlerune.game.world.actor.pawn.update.UpdateFlag
import io.battlerune.net.codec.game.RSByteBufWriter

class PlayerForceChatUpdateBlock : PlayerUpdateBlock(UpdateFlag.FORCED_CHAT) {

    override fun encode(pawn: Player, buffer: RSByteBufWriter) {
        buffer.writeString(pawn.forceChat)
    }

}