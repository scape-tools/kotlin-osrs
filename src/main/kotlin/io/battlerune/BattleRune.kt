package io.battlerune

import io.battlerune.game.GameContext

fun main(args: Array<String>) {

       val context = GameContext()
        context.serviceLoader.start()

}