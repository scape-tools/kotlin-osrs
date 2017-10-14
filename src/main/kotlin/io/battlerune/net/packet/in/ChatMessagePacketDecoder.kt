package io.battlerune.net.packet.`in`

import io.battlerune.game.event.impl.ChatMessageEvent
import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.RSByteBufReader
import io.battlerune.net.packet.PacketDecoder

class ChatMessagePacketDecoder : PacketDecoder<ChatMessageEvent> {

    override fun decode(player: Player, reader: RSByteBufReader): ChatMessageEvent? {

        val packed = reader.readInt24()
        val size = reader.readByte(ByteModification.ADD)

        println("")

        return null

    }

}