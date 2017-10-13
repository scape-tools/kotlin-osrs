package io.battlerune.net.packet.`in`

import io.battlerune.game.event.impl.CommandEvent
import io.battlerune.game.world.actor.pawn.Player
import io.battlerune.net.codec.game.RSByteBufReader
import io.battlerune.net.packet.PacketDecoder

class CommandPacketDecoder : PacketDecoder<CommandEvent> {

    override fun decode(player: Player, reader: RSByteBufReader): CommandEvent {
        return CommandEvent(reader.readString())
    }
}