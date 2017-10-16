package io.battlerune.game.world.scene.collision

class CollisionMatrix(val dimension: Int, val width: Int, val height: Int) {

    private val masks = Array(dimension) { Array(width) { IntArray(height) } }

    fun addMask(plane: Int, localX: Int, localY: Int, mask: Int, add: Boolean) {
        if (localX < 0 || localX >= 64 || localY < 0 || localY >= 64) {
            return
        }
        masks[plane][localX][localY] = (if (add) (masks[plane][localX][localY] or mask) else (masks[plane][localX][localY] and mask.inv()))
    }

    fun clipTile(plane: Int, localX: Int, localY: Int, add: Boolean) {
        addMask(plane,localX, localY, 0x200000, add)
    }

    fun addFloor(plane: Int, localX: Int, localY: Int, add: Boolean) {
        if (localX < 0 || localX >= 64 || localY < 0 || localY >= 64) {
            return
        }
        addMask(plane, localX, localY, 0x40000, add)
    }

    fun setMask(plane: Int, localX: Int, localY: Int, mask: Int) {
        masks[plane][localX][localY] = mask
    }

    fun removeMask(plane: Int, localX: Int, localY: Int, mask: Int) {
        if (localX < 0 || localX >= 64 || localY < 0 || localY >= 64) {
            return
        }
        masks[plane][localX][localY] and mask.inv()
    }

    fun removeFloor(plane: Int, localX: Int, localY: Int) {
        removeMask(plane, localX, localY, 262144)
    }

    fun addObj(plane: Int, localX: Int, localY: Int, sizeX: Int, sizeY: Int, solid: Boolean, notAlternative: Boolean, add:Boolean) {
        var mask = 256

        if (solid) {
            mask = mask or 0x20000
        }

        if (notAlternative) {
            mask = mask or 0x40000000
        }

        for (tileX in localX until (localX + sizeX)) {
            for (tileY in localY until (localY + sizeY)) {
                addMask(plane, tileX, tileY, mask, add)
            }
        }

    }

    fun removeObj(plane: Int, localX: Int, localY: Int, sizeX: Int, sizeY: Int, solid: Boolean, notAlternative: Boolean) {
        var mask = 256

        if (solid) {
            mask = mask or 131072
        }

        if (notAlternative) {
            mask = mask or 1073741824
        }

        for (tileX in localX until (localX + sizeX)) {
            for (tileY in localY until (localY + sizeY)) {
                removeMask(plane, tileX, tileY, mask)
            }
        }
    }

}