package io.battlerune.net.packet.`in`

import io.battlerune.game.event.impl.DialogueContinueEvent
import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.net.codec.game.RSByteBufReader
import io.battlerune.net.packet.PacketDecoder

class DialogueContinuePacketDecoder : PacketDecoder<DialogueContinueEvent> {

    override fun decode(player: Player, reader: RSByteBufReader): DialogueContinueEvent? {
        val hash = reader.readInt()
        var slot = reader.readUShortLE()
        if (slot == 0xFFFF)
            slot = -1
        return DialogueContinueEvent(player, hash, slot)
    }
}