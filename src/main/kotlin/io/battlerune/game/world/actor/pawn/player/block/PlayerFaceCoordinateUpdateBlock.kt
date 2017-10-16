package io.battlerune.game.world.actor.pawn.player.block

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.update.UpdateBlock
import io.battlerune.game.world.actor.pawn.update.BlockType
import io.battlerune.net.codec.game.RSByteBufWriter

class PlayerFaceCoordinateUpdateBlock : UpdateBlock<Player>(0x40, BlockType.FACE_COORDINATE) {

    override fun encode(pawn: Player, buffer: RSByteBufWriter) {
        // TODO
    }

}