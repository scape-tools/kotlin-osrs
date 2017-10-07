package io.battlerune.game.world.actor

import java.util.*
import java.util.stream.IntStream

class PawnList<T : Pawn>(private val capacity: Int) {

    val list = mutableListOf<T?>()
    private val slots = Stack<Int>()

    init {
        for (i in 0 until capacity) {
            list.add(null)

            val slot = capacity - i

            slots.push(slot)
        }
    }

    fun add(t: T) {
        if (slots.isEmpty()) {
            return
        }

        val size = size()

        val slot = slots.pop()

        assert(slot >= 1 && slot < list.size)

        t.index = slot

        list[slot] = t

        println("add slot $slot")

        assert(size == (size + 1) && (size + 1) < list.size)
    }

    fun remove(t: T) {
        assert(t.index >= 1 && t.index < list.size)

        val size = size()

        val slot = t.index

        list[t.index] = null

        slots.push(t.index)

        println("remove slot $slot")

        assert(size == (size - 1) && (size - 1) >= 0)
    }

    fun contains(t: T) : Boolean {
        return t == list[t.index]
    }

    fun isEmpty() : Boolean {
        return slots.isEmpty()
    }

    fun size() : Int {
        return capacity - slots.size
    }

    fun capacity() : Int {
        return capacity
    }

}