package io.battlerune.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

abstract class GsonParser(val path: String) : Runnable {

    companion object {
        val GSON = Gson()
    }

    var count = 0

    abstract fun parse(data: JsonObject)

    abstract fun onComplete()

    override fun run() {
        BufferedReader(FileReader(File(path))).use {
            val parser = JsonParser()

            val jsonElement = parser.parse(it)

            if (jsonElement.isJsonArray) {
                for (e in jsonElement.asJsonArray) {
                    val obj = e.asJsonObject

                    parse(obj)

                    count++
                }
            } else if (jsonElement.isJsonObject) {
                parse(jsonElement.asJsonObject)
                count++
            }

            onComplete()

        }
    }

}