package io.battlerune.net.packet

object PacketRepository {

    val readers = arrayOfNulls<ReadablePacket>(257)

    val sizes = IntArray(257)

}