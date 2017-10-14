package io.battlerune.net.packet.`in`

import io.battlerune.game.event.impl.ClientDimensionChangeEvent
import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.net.codec.game.RSByteBufReader
import io.battlerune.net.packet.PacketDecoder

class ClientDimensionChangePacketDecoder : PacketDecoder<ClientDimensionChangeEvent> {

    override fun decode(player: Player, reader: RSByteBufReader): ClientDimensionChangeEvent {
        val resized = reader.readUByte() == 2
        val width = reader.readUShort()
        val height = reader.readUShort()
        return ClientDimensionChangeEvent(player, width, height, resized)
    }

}