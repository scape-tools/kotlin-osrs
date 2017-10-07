package io.battlerune.game.world.actor

import java.util.*
import java.util.stream.IntStream

class PawnList<T : Pawn>(private val capacity: Int) {

    val list = mutableListOf<T?>()
    private val slots = ArrayDeque<Int>(capacity)
    private var size = 0

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

        list[slot] = t

        size++

        assert(size < list.size)
    }

    fun remove(t: T) {
        if (t.index < 1 || t.index >= list.size) {
            return
        }

        list[t.index] =  null

        slots.add(t.index)

        size--

        assert(size >= 0)
    }

    fun contains(t: T) : Boolean {
        val e = list[t.index] ?: false
        return e == t
    }

    fun isEmpty() : Boolean {
        return size == 0
    }

    fun size() : Int {
        return size
    }

    fun capacity() : Int {
        return capacity
    }

}