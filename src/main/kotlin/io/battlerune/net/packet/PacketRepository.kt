package io.battlerune.net.packet

object PacketRepository {

    val readers = arrayOfNulls<PacketReader>(257)

    val sizes = IntArray(257)

}