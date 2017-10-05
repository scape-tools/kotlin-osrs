package io.battlerune.game.world

import io.battlerune.game.task.StartupTask
import java.io.File

class RegionManager : StartupTask() {

    override fun load() : Boolean {
        val dir = File("./data/xteas/")

        if (!dir.exists()) {
            return false
        }

        val keys = mutableListOf<Int>()

        for (file in dir.listFiles()) {

            val regionId = Integer.parseInt(file.name.substring(0, file.name.indexOf(".")))

            println("region $regionId")

        }


        return true
    }

    override fun onComplete() {
        println("Loaded regions")
    }

}