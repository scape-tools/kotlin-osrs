package io.battlerune.net.packet.`in`

import io.battlerune.game.event.impl.RegionChangeEvent
import io.battlerune.game.world.actor.pawn.Player
import io.battlerune.net.codec.game.RSByteBufReader
import io.battlerune.net.packet.PacketDecoder

class RegionChangePacketDecoder: PacketDecoder<RegionChangeEvent> {

    override fun decode(player: Player, reader: RSByteBufReader): RegionChangeEvent {
        return RegionChangeEvent(player)
    }

}