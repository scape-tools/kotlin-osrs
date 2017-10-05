package io.battlerune.io

import com.google.common.primitives.Ints
import io.battlerune.game.GameContext
import io.battlerune.game.task.StartupTask
import net.openrs.cache.region.Region
import java.io.File
import java.nio.file.Files

class RegionLoader(val gameContext: GameContext) : StartupTask<RegionLoader>(RegionLoader::class.java) {

    override fun load() : Boolean {
        val dir = File("./data/xteas/")

        if (!dir.exists()) {
            return false
        }

        for (file in dir.listFiles()) {

            val keys = mutableListOf<Int>()

            val regionId = Integer.parseInt(file.name.substring(0, file.name.indexOf(".")))

            Files.lines(file.toPath()).forEach { keys.add(Integer.parseInt(it)) }

            val map = gameContext.cache.getFileId(5, "m" + (regionId shr 8) + "_" + (regionId and 0xFF))
            val land = gameContext.cache.getFileId(5, "l" + (regionId shr 8) + "_" + (regionId and 0xFF))

            val region = Region(regionId)

            if (map != -1) {
                region.loadTerrain(gameContext.cache.read(5, map).data)
            }

            if (land != -1) {
                region.loadLocations(gameContext.cache.read(5, land, Ints.toArray(keys)).data)
            }

            gameContext.regionManager.regions[regionId] = region

        }

        return true
    }

    override fun onComplete() {
        logger.info("Loaded regions")
    }

}