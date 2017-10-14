package io.battlerune.game.world.actor.pawn

import java.util.Deque
import io.battlerune.game.world.Position
import io.battlerune.game.world.actor.pawn.player.Player
import java.util.LinkedList

class Movement(val entity: Pawn) {

    val focusPoints: Deque<MovementPoint> = LinkedList()

    var running = true

    var isRunningQueueEnabled = false

    val nextPoint: MovementPoint?
        get() {
            val availableFocusPoint = focusPoints.poll()

            if (availableFocusPoint == null || availableFocusPoint.direction == -1) {
                return null
            } else {
                entity.position.transform(DIRECTION_DELTA_X[availableFocusPoint.direction].toInt(), DIRECTION_DELTA_Y[availableFocusPoint.direction].toInt(), entity.position.z)
                return availableFocusPoint
            }
        }

    val isMoving: Boolean
        get() = !focusPoints.isEmpty()

    val isMovementDone: Boolean
        get() = focusPoints.isEmpty()

    fun finish() {
        focusPoints.removeFirst()
    }

    fun reset() {
        isRunningQueueEnabled = false
        focusPoints.clear()
        focusPoints.add(MovementPoint(entity.position.x, entity.position.y, -1))
    }

    fun handleEntityMovement() {
        var walkingPoint: MovementPoint? = nextPoint
        var runningPoint: MovementPoint? = null

        if (running) {
            runningPoint = nextPoint
        }

        entity.walkingDirection = (if (walkingPoint == null) -1 else walkingPoint.direction)

        entity.runningDirection = (if (runningPoint == null) -1 else runningPoint.direction)

        val deltaX = entity.position.x - entity.lastPosition.regionX * 8
        val deltaY = entity.position.y - entity.lastPosition.regionY * 8

        if (entity is Player) {
            if (deltaX < 16 || deltaX >= 88 || deltaY < 16 || deltaY > 88) {
                //entity.getPlayer().queuePacket(UpdateMapRegion())
            }

            if (walkingPoint != null || runningPoint != null) {
                entity.handleMovement()
            }
        }
    }

    fun stop() {
        isRunningQueueEnabled = false
        focusPoints.clear()
        focusPoints.add(MovementPoint(entity.position.x, entity.position.y, -1))
    }

    fun walk(location: Position) {
        reset()
        addToPath(location)
        finish()
    }

    fun addToPath(location: Position) {

        if (focusPoints.isEmpty()) {
            reset()
        }

        val last = focusPoints.peekLast()

        var deltaX = location.x - last.x

        var deltaY = location.y - last.y

        val max = Math.max(Math.abs(deltaX), Math.abs(deltaY))

        for (i in 0 until max) {

            if (deltaX < 0) {
                deltaX++
            } else if (deltaX > 0) {
                deltaX--
            }

            if (deltaY < 0) {
                deltaY++
            } else if (deltaY > 0) {
                deltaY--
            }

            addStep(location.x - deltaX, location.y - deltaY)
        }
    }

    private fun addStep(x: Int, y: Int) {

        if (focusPoints.size >= 50) {
            return
        }

        val lastPosition = focusPoints.peekLast()

        val direction = parseDirection(x - lastPosition.x, y - lastPosition.y)

        if (direction > -1) {
            focusPoints.add(MovementPoint(x, y, direction))
        }
    }

    inner class MovementPoint(val x: Int, val y: Int, val direction: Int)

    companion object {

        private val DIRECTION_DELTA_X = byteArrayOf(-1, 0, 1, -1, 1, -1, 0, 1)
        private val DIRECTION_DELTA_Y = byteArrayOf(1, 1, 1, 0, 0, -1, -1, -1)

        fun parseDirection(deltaX: Int, deltaY: Int): Int {
            if (deltaX < 0) {
                if (deltaY < 0) {
                    return 5
                }
                return if (deltaY > 0) {
                    0
                } else 3
            }
            if (deltaX > 0) {
                if (deltaY < 0) {
                    return 7
                }
                return if (deltaY > 0) {
                    2
                } else 4
            }
            if (deltaY < 0) {
                return 6
            }
            return if (deltaY > 0) {
                1
            } else -1
        }

    }

}