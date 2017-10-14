package io.battlerune.game.world.actor.pawn

import io.battlerune.game.world.Position
import io.battlerune.game.world.Region
import io.battlerune.game.world.actor.Actor
import java.util.*

abstract class Pawn : Actor() {

    lateinit var  region: Region

    var position = Position(3222, 3222, 0, Position.RegionSize.DEFAULT)
    var lastPosition = Position(3222, 3222, 0, Position.RegionSize.DEFAULT)

    var index = -1

    val updateFlags = EnumSet.noneOf(UpdateFlag::class.java)

    var regionChanged = false

    var walkingDirection = -1
    var runningDirection = -1

    abstract fun preUpdate()
    abstract fun update()
    abstract fun postUpdate()

    fun handleMovement() {
        onMovement()
    }

    abstract fun onMovement()

}