package io.battlerune.game.world

import java.nio.ByteBuffer
import net.openrs.util.ByteBufferUtils.getUnsignedSmart

/**
 * See
 *
 * https://github.com/kfricilone/OpenRS/blob/master/source/net/openrs/cache/region/Region.java
 */
class Region(val regionID: Int) {

    companion object {
        /**
         * Indicates a tile at the current position is not walkable.
         */
        private val BLOCKED_TILE = 0x1

        /**
         * Indicates a tile at the current position is a bridged tile.
         */
        private val BRIDGE_TILE = 0x2
    }

    fun decodeTerrain(buf: ByteBuffer) {
        for (height in 0..3) {
            for (localX in 0..63) {
                for (localY in 0..63) {
                    var attributes = 0

                    while (true) {
                        val attributeId = buf.get().toInt() and 0xFF

                        if (attributeId == 0) {
                            decodeAttributes(attributes, (regionID shr 8 and 0xFF) * 64 + localX,
                                    (regionID and 0xFF) * 64 + localY, height)
                            break
                        } else if (attributeId == 1) {
                            buf.get()
                            decodeAttributes(attributes, (regionID shr 8 and 0xFF) * 64 + localX,
                                    (regionID and 0xFF) * 64 + localY, height)
                            break
                        } else if (attributeId <= 49) {
                            buf.get()
                        } else if (attributeId <= 81) {
                            attributes = attributeId - 49
                        }
                    }
                }
            }
        }
    }

    /**
     * Decodes object data stored in the specified [ByteBuffer].
     *
     * @param buffer
     * The ByteBuffer.
     */
    fun decodeStaticObjects(buffer: ByteBuffer) {
        var id = -1
        var idOffset = getUnsignedSmart(buffer)

        while (idOffset != 0) {
            id += idOffset

            var packed = 0
            var positionOffset = getUnsignedSmart(buffer)

            while (positionOffset != 0) {
                packed += positionOffset - 1

                val localY = packed and 0x3F
                val localX = packed shr 6 and 0x3F
                val height = packed shr 12 and 0x3

                val attributes = buffer.get().toInt() and 0xFF
                val type = attributes shr 2
                val orientation = attributes and 0x3
                val position = Position((regionID shr 8 and 0xFF) * 64 + localX,
                        (regionID and 0xFF) * 64 + localY, height)

                positionOffset = getUnsignedSmart(buffer)
            }

            idOffset = getUnsignedSmart(buffer)
        }
    }

    /**
     * Decodes the attributes of a terrain file, blocking the tile if necessary.
     *
     * @param attributes
     * The terrain attributes.
     * @param x
     * The x coordinate of the tile the attributes belong to.
     * @param y
     * The y coordinate of the tile the attributes belong to.
     * @param height
     * The level level of the tile the attributes belong to.
     */
    private fun decodeAttributes(attributes: Int, x: Int, y: Int, height: Int) {
        var height = height
        var block = false
        if (attributes and BLOCKED_TILE != 0) {
            block = true
        }

        if (attributes and BRIDGE_TILE != 0) {
            if (height > 0) {
                block = true
                height--
            }
        }

        if (block) {
            val localX = x % 8
            val localY = y % 8
            //matrices[height].block(localX, localY)
        }
    }


}