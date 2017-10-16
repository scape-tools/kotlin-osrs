package io.battlerune.game.world.actor.pawn

/**
 * Represents an in-game chat message.
 *
 *  @author nshusa
 */
class ChatMessage(val msg: String = "", val color: ChatColor = ChatColor.YELLOW, val effect: ChatEffect = ChatEffect.NONE) {

    companion object {
        val MAX_CHARACTERS = 256
        val MAX_COLORS = 12
        val MAX_EFFECTS = 6

        fun isValid(msg: String, color: ChatColor, effect: ChatEffect) : Boolean {
            return msg.isNotEmpty() && msg.length < MAX_CHARACTERS && color.code >= 0 && color.code < MAX_COLORS && effect.code >= 0 && effect.code < MAX_EFFECTS
        }

        fun isValid(msg: String, color: Int, effect: Int) : Boolean {
            return msg.isNotEmpty() && msg.length < MAX_CHARACTERS && color >= 0 && color < MAX_COLORS && effect >= 0 && effect < MAX_EFFECTS
        }
    }

    /**
     * Represents a color effect that can be used on a chat message.
     *
     * Order is really important, do not change the order.
     *
     *  @author nshusa
     */
    enum class ChatColor(val code: Int) {
        YELLOW(0),
        RED(1),
        GREEN(2),
        CYAN(3),
        PURPLE(4),
        WHITE(5),
        FLASH_1(6),
        FLASH_2(7),
        FLASH_3(8),
        GLOW_1(9),
        GLOW_2(10),
        GLOW_3(11)
    }

    /**
     * Represents a non-color effect that can be used on a chat message.
     *
     * Order is really important, do not change the order.
     *
     *  @author nshusa
     */
    enum class ChatEffect(val code: Int) {
        NONE(0),
        WAVE(1),
        WAVE_2(2),
        SHAKE(3),
        SCROLL(4),
        SLIDE(5)
    }

}