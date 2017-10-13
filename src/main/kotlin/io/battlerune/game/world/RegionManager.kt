package io.battlerune.game.world

import com.google.common.collect.ArrayListMultimap

class RegionManager {

    private val regions = arrayOfNulls<Region>(32768)

    val keys: ArrayListMultimap<Int, Int> = ArrayListMultimap.create()

    fun set(regionId: Int, region: Region) {
        if (regionId < 0 || regionId >= regions.size) {
            throw IllegalStateException("regionId $regionId must be >= 0 and < ${regions.size}")
        }

        regions[regionId] = region
    }

    fun lookup(regionId: Int) : Region {
        if (regionId < 0 || regionId >= regions.size) {
            throw IllegalStateException("regionId $regionId must be >= 0 and < ${regions.size}")
        }

        return regions[regionId] ?: throw IllegalStateException("region=$regionId should not be null")
    }

    fun count() : Int {
        return regions.size
    }

}