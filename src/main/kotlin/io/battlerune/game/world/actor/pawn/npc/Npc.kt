package io.battlerune.game.world.actor.pawn.npc

import io.battlerune.game.world.actor.pawn.Pawn

class Npc : Pawn() {

    override fun preUpdate() {
        movement.processMovement()
    }

    override fun update() {

    }

    override fun postUpdate() {

    }

    override fun onMovement() {

    }

}