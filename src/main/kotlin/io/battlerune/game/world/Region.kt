package io.battlerune.game.world

import io.battlerune.game.world.scene.collision.CollisionMatrix
import io.battlerune.game.world.scene.MapObject

class Region(val regionID: Int) {

    val collsionMatrix = CollisionMatrix(4, 64, 64)
    val floors = Array(4) { Array(64) { ByteArray(64) } }
    val objects = Array(4) { Array(64) { arrayOfNulls<MapObject>(64) } }

}