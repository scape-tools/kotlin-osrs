package io.battlerune.game.world.actor.pawn

class Graphic(val id: Int, val delay: Int = 0) {

    companion object {
        val RESET = Graphic(65535, 0)
    }

}