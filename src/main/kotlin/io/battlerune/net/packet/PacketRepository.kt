package io.battlerune.net.packet

import io.battlerune.game.event.Event

object PacketRepository {

    val decoders = arrayOfNulls<PacketDecoder<Event>>(257)

    val sizes = IntArray(257)

}