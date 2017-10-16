package io.battlerune.game.world.actor.pawn

import java.util.Deque
import io.battlerune.game.world.Position
import io.battlerune.game.world.actor.pawn.player.Player
import java.util.LinkedList

class Movement(val pawn: Pawn) {

    val steps: Deque<Step> = LinkedList()

    var walkingDirection = -1
    var runningDirection = -1

    var running = true

    var isRunningQueueEnabled = false

    val nextPoint: Step?
        get() {
            val availableFocusPoint = steps.poll()

            if (availableFocusPoint == null || availableFocusPoint.direction == -1) {
                return null
            } else {
                pawn.position.transform(DIRECTION_DELTA_X[availableFocusPoint.direction].toInt(), DIRECTION_DELTA_Y[availableFocusPoint.direction].toInt(), pawn.position.z)
                return availableFocusPoint
            }
        }

    val isMoving: Boolean
        get() = !steps.isEmpty()

    val isMovementDone: Boolean
        get() = steps.isEmpty()

    fun finish() {
        steps.removeFirst()
    }

    fun reset() {
        isRunningQueueEnabled = false
        steps.clear()
        steps.add(Step(pawn.position.x, pawn.position.y, -1))
    }

    fun processMovement() {
        var walkingPoint: Step? = nextPoint
        var runningPoint: Step? = null

        if (running) {
            runningPoint = nextPoint
        }

        walkingDirection = (if (walkingPoint == null) -1 else walkingPoint.direction)

        runningDirection = (if (runningPoint == null) -1 else runningPoint.direction)

        val deltaX = pawn.position.x - pawn.lastPosition.regionX * 8
        val deltaY = pawn.position.y - pawn.lastPosition.regionY * 8

        if (pawn is Player) {
            if (deltaX < 16 || deltaX >= 88 || deltaY < 16 || deltaY > 88) {
                //entity.getPlayer().queuePacket(UpdateMapRegion())
            }

            if (walkingPoint != null || runningPoint != null) {
                pawn.onMovement()
            }
        }
    }

    fun stop() {
        isRunningQueueEnabled = false
        steps.clear()
        steps.add(Step(pawn.position.x, pawn.position.y, -1))
    }

    fun walk(location: Position) {
        reset()
        addToPath(location)
        finish()
    }

    fun addToPath(location: Position) {

        if (steps.isEmpty()) {
            reset()
        }

        val last = steps.peekLast()

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

        if (steps.size >= 50) {
            return
        }

        val lastPosition = steps.peekLast()

        val direction = parseDirection(x - lastPosition.x, y - lastPosition.y)

        if (direction > -1) {
            steps.add(Step(x, y, direction))
        }
    }

    inner class Step(val x: Int, val y: Int, val direction: Int)

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