package io.battlerune.game.world.actor.pawn.player.block

import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.update.PlayerUpdateBlock
import io.battlerune.game.world.actor.pawn.update.UpdateFlag
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.RSByteBufWriter

class PlayerAppearanceUpdateBlock : PlayerUpdateBlock(0x2, UpdateFlag.APPEARANCE) {

    override fun encode(pawn: Player, buffer: RSByteBufWriter) {
        val prop = RSByteBufWriter.alloc(64)
        prop.writeByte(pawn.appearance.gender.code) // gender
        prop.writeByte(if (pawn.skulled) 1 else -1) // skulled
        prop.writeByte(-1) // head icon

        // slots
        for (i in 0 until 4) {
            prop.writeByte(0)
        }

        prop.writeShort(0x100 + pawn.appearance.style[2]) // chest
        prop.writeByte(0) // shield
        prop.writeShort(0x100 + pawn.appearance.style[3]) // full body
        prop.writeShort(0x100 + pawn.appearance.style[5]) // legs
        prop.writeShort(0x100 + pawn.appearance.style[0]) // hat
        prop.writeShort(0x100 + pawn.appearance.style[4]) // hands
        prop.writeShort(0x100 + pawn.appearance.style[6]) // feet
        prop.writeShort(0x100 + pawn.appearance.style[1])

        // colors
        for (i in 0 until 5) {
            prop.writeByte(0)
        }

        prop.writeShort(808) // stand anim
                .writeShort(823) // stand turn
                .writeShort(819) // walk anim
                .writeShort(820) // turn 180
                .writeShort(821) // turn 90 cw
                .writeShort(822) // turn 90 ccw
                .writeShort(824) // run anima
                .writeString(pawn.username) // username
                .writeByte(126) // combat level
                .writeShort(0) // skill level
                .writeByte(0) // hidden

        buffer.writeByte(prop.buffer.writerIndex() and 0xFF, ByteModification.ADD)
        buffer.writeBytes(prop.buffer, ByteModification.ADD)
    }

}