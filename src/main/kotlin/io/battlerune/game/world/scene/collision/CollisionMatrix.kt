package io.battlerune.game.world.scene.collision

class CollisionMatrix(val width: Int, val height: Int) {

    var masks = Array(width, {IntArray(height)})

    fun addMask(localX: Int, localY: Int, mask: Int, add: Boolean) {
        if (localX < 0 || localX >= 64 || localY < 0 || localY >= 64) {
            return
        }

        masks[localX][localY] = (if (add) (masks[localX][localY] or mask) else (masks[localX][localY] and mask.inv()))
    }

    fun clipTile(localX: Int, localY: Int, add: Boolean) {
        addMask(localX, localY, 0x200000, add)
    }

    fun addFloor(localX: Int, localY: Int, add: Boolean) {
        if (localX < 0 || localX >= 64 || localY < 0 || localY >= 64) {
            return
        }
        addMask(localX, localY, 0x40000, add)
    }

    fun setMask(localX: Int, localY: Int, mask: Int) {
        masks[localX][localY] = mask
    }

    fun removeMask(localX: Int, localY: Int, mask: Int) {
        if (localX < 0 || localX >= 64 || localY < 0 || localY >= 64) {
            return
        }
        masks[localX][localY] and mask.inv()
    }

    fun removeFloor(localX: Int, localY: Int) {
        removeMask(localX, localY, 262144)
    }

    fun addObj(localX: Int, localY: Int, sizeX: Int, sizeY: Int, solid: Boolean, notAlternative: Boolean, add:Boolean) {
        var mask = 256

        if (solid) {
            mask = mask or 0x20000
        }

        if (notAlternative) {
            mask = mask or 0x40000000
        }

        for (tileX in localX until (localX + sizeX)) {
            for (tileY in localY until (localY + sizeY)) {
                addMask(tileX, tileY, mask, add)
            }
        }

    }

    fun removeObj(localX: Int, localY: Int, sizeX: Int, sizeY: Int, solid: Boolean, notAlternative: Boolean) {
        var mask = 256

        if (solid) {
            mask = mask or 131072
        }

        if (notAlternative) {
            mask = mask or 1073741824
        }

        for (tileX in localX until (localX + sizeX)) {
            for (tileY in localY until (localY + sizeY)) {
                removeMask(tileX, tileY, mask)
            }
        }
    }

}