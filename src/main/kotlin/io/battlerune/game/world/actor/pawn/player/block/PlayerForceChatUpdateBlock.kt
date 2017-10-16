package io.battlerune.game.world.actor.pawn.player.block

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.update.UpdateBlock
import io.battlerune.game.world.actor.pawn.update.UpdateFlag
import io.battlerune.net.codec.game.RSByteBufWriter

class PlayerForceChatUpdateBlock : UpdateBlock<Player>(0x1, UpdateFlag.FORCED_CHAT) {

    override fun encode(pawn: Player, buffer: RSByteBufWriter) {
        buffer.writeString(pawn.forceChat)
    }

}