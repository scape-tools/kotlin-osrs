package io.battlerune.game.world.actor.pawn.player.block

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.update.UpdateBlock
import io.battlerune.game.world.actor.pawn.update.UpdateFlag
import io.battlerune.net.codec.game.RSByteBufWriter

class PlayerFacePawnUpdateBlock : UpdateBlock<Player>(0x80, UpdateFlag.FACE_PAWN) {

    override fun encode(pawn: Player, buffer: RSByteBufWriter) {
        // TODO
    }

}