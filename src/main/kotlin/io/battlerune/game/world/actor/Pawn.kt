package io.battlerune.game.world.actor

import io.battlerune.game.world.Position
import io.battlerune.game.world.Region
import io.battlerune.game.world.World
import java.util.*

abstract class Pawn : Actor() {

    lateinit var  region: Region

    var position = Position(3222, 3222, 0, Position.RegionSize.DEFAULT)
    var lastPosition = Position(3222, 3222, 0, Position.RegionSize.DEFAULT)

    val updateFlags = EnumSet.noneOf(UpdateFlag::class.java)

    var regionChanged = false

    abstract fun preUpdate()
    abstract fun update()
    abstract fun postUpdate()

    abstract fun init()

}