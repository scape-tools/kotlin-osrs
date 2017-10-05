package io.battlerune.game.world.actor

import net.openrs.cache.region.Position

open class Pawn : Actor() {

    val position = Position(3222, 3222, 0, Position.RegionSize.DEFAULT)
    val lastPosition = Position(3222, 3222, 0, Position.RegionSize.DEFAULT)

}