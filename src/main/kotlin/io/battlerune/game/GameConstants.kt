package io.battlerune.game

import com.moandjiezana.toml.Toml
import java.io.File

object GameConstants {

    val SERVER_NAME: String

    init {
        val parser = Toml().read(File("./settings.toml")).getTable("game")

        try {
            SERVER_NAME = parser.getString("server_name")
        } catch (ex: Exception) {
            throw ExceptionInInitializerError(ex)
        }
    }

}