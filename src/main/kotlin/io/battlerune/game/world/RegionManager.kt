package io.battlerune.game.world

import net.openrs.cache.region.Region

class RegionManager {

    private val regions = arrayOfNulls<Region>(32768)

    fun set(regionId: Int, region: Region) {
        if (regionId < 0 || regionId >= regions.size) {
            throw IllegalStateException("regionId $regionId must be >= 0 and < ${regions.size}")
        }

        regions[regionId] = region
    }

    fun lookup(regionId: Int) : Region? {
        if (regionId < 0 || regionId >= regions.size) {
            throw IllegalStateException("regionId $regionId must be >= 0 and < ${regions.size}")
        }

        return regions[regionId]
    }

    fun count() : Int {
        return regions.size
    }

}