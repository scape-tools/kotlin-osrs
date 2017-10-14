package io.battlerune.content

import com.google.common.eventbus.Subscribe
import io.battlerune.game.world.actor.pawn.Player

class RegionChangeEventListener {

    @Subscribe
    fun onEvent(player: Player) {
        player.regionChanged = true

        println("a region changed")

    }

}