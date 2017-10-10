package io.battlerune.util.extensions

fun String.ipToInt(): Int {
    val array = split(".")
    var ip = 0
    for (i in 0 until array.size) {
        val power = 3 - i

        ip += (array[i].toInt()) % 256 * Math.pow(256.toDouble(), power.toDouble()).toInt()

    }
    return ip
}