package io.battlerune.net.packet.`in`

import io.battlerune.game.event.impl.MovementEvent
import io.battlerune.game.world.Position
import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.net.codec.game.ByteModification
import io.battlerune.net.codec.game.RSByteBufReader
import io.battlerune.net.packet.PacketDecoder

class ClickToWalkPacketDecoder : PacketDecoder<MovementEvent> {

    override fun decode(player: Player, reader: RSByteBufReader): MovementEvent {
        reader.readByte() // constant 5
        reader.readByte(ByteModification.SUB) // type
        val x = reader.readShortLE(ByteModification.ADD)
        val y = reader.readShort()
        return MovementEvent(player, Position(x, y, player.position.z))
    }

}