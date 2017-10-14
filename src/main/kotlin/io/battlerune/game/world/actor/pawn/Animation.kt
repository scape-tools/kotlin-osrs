package io.battlerune.game.world.actor.pawn

class Animation(val id: Int, val delay: Int) {

    companion object {
        val RESET = Animation(65535, 0)
    }

}