package io.battlerune.game.world.actor.pawn.update

import io.battlerune.game.world.actor.pawn.Pawn
import io.battlerune.net.codec.game.RSByteBufWriter

abstract class UpdateBlock<in P : Pawn>(val flag: UpdateFlag) {

    abstract fun encode(pawn: P, buffer: RSByteBufWriter)

}