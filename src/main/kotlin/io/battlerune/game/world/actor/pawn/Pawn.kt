package io.battlerune.game.world.actor.pawn

import io.battlerune.game.world.Position
import io.battlerune.game.world.Region
import io.battlerune.game.world.actor.Actor
import io.battlerune.game.world.actor.pawn.update.UpdateFlag
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

    var graphic = Graphic.RESET
    var animation = Animation.RESET

    abstract fun preUpdate()
    abstract fun update()
    abstract fun postUpdate()

    fun handleMovement() {
        onMovement()
    }

    abstract fun onMovement()

    fun startGfx(id: Int, height: Int = 92, delay: Int = 0) {
        graphic = Graphic(id, height, delay)
        updateFlags.add(UpdateFlag.GFX)
    }

    fun resetGfx() {
        graphic = Graphic.RESET
        updateFlags.add(UpdateFlag.GFX)
    }

    fun startAnim(id: Int, delay: Int = 0) {
        animation = Animation(id, delay)
        updateFlags.add(UpdateFlag.ANIMATION)
    }

    fun resetAnim() {
        animation = Animation.RESET
        updateFlags.add(UpdateFlag.ANIMATION)
    }

}