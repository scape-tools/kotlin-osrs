package io.battlerune.game

import io.battlerune.game.world.RegionManager
import io.battlerune.game.world.World
import net.openrs.cache.Cache
import java.nio.ByteBuffer

class GameContext {

    val regionManager = RegionManager()
    lateinit var world: World
    lateinit var cache: Cache
    lateinit var checksumTable: ByteBuffer

}