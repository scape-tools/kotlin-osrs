package io.battlerune.game.world.actor

import net.openrs.cache.region.Position

open abstract class Pawn : Actor() {

    var position = Position(3222, 3222, 0, Position.RegionSize.DEFAULT)
    var lastPosition = Position(3222, 3222, 0, Position.RegionSize.DEFAULT)

    abstract fun init()

}