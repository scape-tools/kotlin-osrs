package io.battlerune.game.widget

enum class DisplayType(val root: Int) {

    FIXED(548),

    RESIZABLE(161),

    RESIZABLE_PANEL(164);

    companion object {

        fun lookup(id: Int) : DisplayType {
            if (id < 0 || id >= DisplayType.values().size) {
                throw IllegalStateException("id=$id must be >= 0 and < ${DisplayType.values().size}")
            }

            return DisplayType.values()[id]
        }

    }

}