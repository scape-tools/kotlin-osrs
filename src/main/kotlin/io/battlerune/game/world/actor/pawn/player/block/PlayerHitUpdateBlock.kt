package io.battlerune.game.world.actor.pawn.player.block

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.update.PlayerUpdateBlock
import io.battlerune.game.world.actor.pawn.update.UpdateFlag
import io.battlerune.net.codec.game.RSByteBufWriter

class PlayerHitUpdateBlock : PlayerUpdateBlock(0x20, UpdateFlag.HIT) {

    override fun encode(pawn: Player, buffer: RSByteBufWriter) {
        // TODO
    }

}