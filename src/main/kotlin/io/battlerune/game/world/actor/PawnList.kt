package io.battlerune.game.world.actor

import java.util.*
import java.util.stream.IntStream

class PawnList<T : Pawn>(capacity: Int) {

    val list = mutableListOf<T?>()
    val slots = ArrayDeque<Int>(capacity)

    init {
        IntStream.rangeClosed(0, capacity).forEach { list.add(null) }
        IntStream.rangeClosed(1, capacity).forEach { slots.add(it) }
    }

    fun add(t: T) {
        if (slots.isEmpty()) {
            return
        }

        val slot = slots.poll()

        if (slot < 1 || slot >= list.size) {
            return
        }

        t.index = slot
    }

    fun remove(t: T) {
        if (t.index < 1 || t.index >= list.size) {
            return
        }

        list[t.index] =  null

        slots.add(t.index)
    }

    fun contains(t: T) : Boolean {
        val e = list[t.index] ?: false
        return e == t
    }

}