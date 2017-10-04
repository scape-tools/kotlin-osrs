package io.battlerune.net.packet

object PacketHandlerRepository {

    val readers = arrayOfNulls<PacketReader>(257)

    val sizes = IntArray(257)

}