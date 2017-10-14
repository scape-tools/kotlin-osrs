package io.battlerune.net.packet.`in`

import io.battlerune.game.event.impl.CommandEvent
import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.game.world.actor.pawn.player.cmd.CommandParser
import io.battlerune.net.codec.game.RSByteBufReader
import io.battlerune.net.packet.PacketDecoder

class CommandPacketDecoder : PacketDecoder<CommandEvent> {

    override fun decode(player: Player, reader: RSByteBufReader): CommandEvent? {
        val cmd = reader.readString()

        if (cmd.isEmpty()) {
            return null
        }

        return CommandEvent(player, CommandParser(cmd))
    }
}