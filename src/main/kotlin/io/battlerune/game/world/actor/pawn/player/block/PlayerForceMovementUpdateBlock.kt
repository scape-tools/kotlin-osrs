package io.battlerune.game.world.actor.pawn.player.block

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.update.UpdateBlock
import io.battlerune.game.world.actor.pawn.update.UpdateFlag
import io.battlerune.net.codec.game.RSByteBufWriter

class PlayerForceMovementUpdateBlock : UpdateBlock<Player>(0x200, UpdateFlag.FORCE_MOVEMENT) {

    override fun encode(pawn: Player, buffer: RSByteBufWriter) {
        // TODO implement
    }

}