package io.battlerune.game.world.actor.pawn

import io.battlerune.game.world.Position
import io.battlerune.game.world.Region
import io.battlerune.game.world.actor.Actor
import io.battlerune.game.world.actor.pawn.update.BlockType
import java.util.*

abstract class Pawn : Actor() {

    lateinit var  region: Region

    var position = Position(3222, 3222, 0, Position.RegionSize.DEFAULT)
    var lastPosition = Position(3222, 3222, 0, Position.RegionSize.DEFAULT)

    var index = -1

    val updateFlags = EnumSet.noneOf(BlockType::class.java)

    var forceChat = ""

    var regionChanged = false

    var graphic = Graphic.RESET
    var animation = Animation.RESET

    val movement = Movement(this)

    abstract fun preUpdate()
    abstract fun update()
    abstract fun postUpdate()
    abstract fun onMovement()

    fun playGfx(id: Int, height: Int = 92, delay: Int = 0) {
        graphic = Graphic(id, height, delay)
        updateFlags.add(BlockType.GFX)
    }

    fun resetGfx() {
        graphic = Graphic.RESET
        updateFlags.add(BlockType.GFX)
    }

    fun playAnim(id: Int, delay: Int = 0) {
        animation = Animation(id, delay)
        updateFlags.add(BlockType.ANIMATION)
    }

    fun resetAnim() {
        animation = Animation.RESET
        updateFlags.add(BlockType.ANIMATION)
    }

    fun forceChat(msg: String) {
        if (forceChat.isEmpty()) {
            return
        }

        forceChat = msg.trim()
        updateFlags.add(BlockType.FORCED_CHAT)
    }



}