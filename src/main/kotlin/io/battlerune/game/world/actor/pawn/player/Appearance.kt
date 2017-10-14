package io.battlerune.game.world.actor.pawn.player

class Appearance(val gender: Gender = Gender.MALE, val style: IntArray = intArrayOf(0, 10, 18, 26, 33, 36, 42), val colors: IntArray = intArrayOf(0, 0, 0, 0, 0)) {

    init {
        assert(style.size <= MAX_STYLES)
        assert(colors.size <= MAX_COLORS)
    }

    companion object {
        val DEFAULT = Appearance()

        enum class Gender(val code: Int) {
            MALE(0x0),
            FEMALE(0x1)
        }

        val MAX_STYLES = 7
        val MAX_COLORS = 5
    }

}