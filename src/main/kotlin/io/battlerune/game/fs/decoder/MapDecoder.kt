package io.battlerune.game.fs.decoder

import io.battlerune.game.world.scene.MapObject
import io.battlerune.game.world.Position
import io.battlerune.game.world.Region
import io.battlerune.game.world.scene.collision.TileMask
import net.openrs.util.ByteBufferUtils
import java.nio.ByteBuffer

object MapDecoder {

    fun decodeTerrain(region: Region, mapBuf: ByteBuffer) {
        for (height in 0..3) {
            for (localX in 0..63) {
                for (localY in 0..63) {
                    while (true) {
                        val attributeId = mapBuf.get().toInt() and 0xFF

                        if (attributeId == 0) {
                            break
                        } else if (attributeId == 1) {
                            mapBuf.get()
                            break
                        } else if (attributeId <= 49) {
                            mapBuf.get()
                        } else if (attributeId <= 81) {
                            region.floors[height][localX][localY] = (attributeId - 49).toByte()
                        }
                    }

                    var realPlane = height

                    val floor = region.floors[height][localX][localY].toInt()

                    if (floor and TileMask.BLOCKED_TILE == 1) {

                        if (floor and TileMask.BRIDGE_TILE == 2) {
                            realPlane--
                        }

                        if (realPlane >= 0) {
                            region.collsionMatrix.addFloor(realPlane, localX, localY, true)
                        }

                    }

                }
            }
        }

    }

    fun decodeStaticObjects(region: Region, objBuf: ByteBuffer) {
        var id = -1
        var idOffset = ByteBufferUtils.getUnsignedSmart(objBuf)

        while (idOffset != 0) {
            id += idOffset

            var packed = 0
            var positionOffset = ByteBufferUtils.getUnsignedSmart(objBuf)

            while (positionOffset != 0) {
                packed += positionOffset - 1

                val localY = packed and 0x3F
                val localX = packed shr 6 and 0x3F
                val height = packed shr 12 and 0x3

                val attributes = objBuf.get().toInt() and 0xFF
                val type = attributes shr 2
                val orientation = attributes and 0x3
                val position = Position((region.regionID shr 8 and 0xFF) * 64 + localX,(region.regionID and 0xFF) * 64 + localY, height)

                region.objects[height][localX][localY] = MapObject(id, position, type, orientation)

                positionOffset = ByteBufferUtils.getUnsignedSmart(objBuf)
            }

            idOffset = ByteBufferUtils.getUnsignedSmart(objBuf)
        }
    }

}