package io.battlerune.game

import io.battlerune.game.service.ServiceLoader
import io.battlerune.game.world.World
import net.openrs.cache.Cache
import java.nio.ByteBuffer

class GameContext {

    companion object {
        lateinit var cache: Cache
        lateinit var checksumTable: ByteBuffer
    }

    val serviceLoader = ServiceLoader(this)
    lateinit var world: World

}