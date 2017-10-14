package io.battlerune.game.world

import org.lwjgl.opengl.Display.getHeight



/**
 * Copyright (c) Kyle Fricilone
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

class Position constructor(val x: Int, val y: Int, val z: Int = 0, val mapSize: RegionSize = RegionSize.DEFAULT) {

    val xInRegion: Int
        get() = x and 0x3F

    val yInRegion: Int
        get() = y and 0x3F

    val localX: Int
        get() = x - 8 * (chunkX - (mapSize.size shr 4))

    val localY: Int
        get() = y - 8 * (chunkY - (mapSize.size shr 4))

    val chunkX: Int
        get() = x shr 3

    val chunkY: Int
        get() = y shr 3

    val regionX: Int
        get() = x shr 6

    val regionY: Int
        get() = y shr 6

    val regionID: Int
        get() = (regionX shl 8) + regionY

    enum class RegionSize(val size: Int) {
        DEFAULT(104), LARGE(120), XLARGE(136), XXLARGE(168)
    }

    constructor(localX: Int, localY: Int, height: Int, regionId: Int, mapSize: RegionSize) : this(localX + (regionId shr 8 and 0xFF shl 6), localX + (regionId and 0xff shl 6), height, mapSize)

    fun getLongestDelta(other: Position): Int {
        val deltaX = Math.abs(x - other.x)
        val deltaY = Math.abs(y - other.y)
        return Math.max(deltaX, deltaY)
    }

    fun getLocalX(pos: Position): Int {
        return x - 8 * (pos.chunkX - (mapSize.size shr 4))
    }

    fun getLocalY(pos: Position): Int {
        return y - 8 * (pos.chunkY - (mapSize.size shr 4))
    }

    fun toRegionPacked(): Int {
        return regionY + (regionX shl 8) + (z shl 16)
    }

    fun toPositionPacked(): Int {
        return y + (x shl 14) + (z shl 28)
    }

    fun toAbsolute(): Position {
        val xOff = x % 8
        val yOff = y % 8
        return Position(x - xOff, y - yOff, z)
    }

    fun withinDistance(other: Position, distance: Int) : Boolean {
        if (z != other.z) {
            return false
        }

        val deltaX = other.x - x
        val deltaY = other.y - y

        return Math.abs(deltaX) <= distance && Math.abs(deltaY) <= distance
    }

    fun transform(x: Int, y: Int, z: Int) {
        this.x + x
        this.y + y
        this.z + z
    }

    override fun toString(): String {
        return "Position=[X: $x, Y: $y, z: $z]"
    }

}