package io.battlerune.game.world.actor.pawn

class Graphic(val id: Int, val height: Int = 92, val delay: Int = 0) {

    companion object {
        val RESET = Graphic(65535, 92)
    }

}