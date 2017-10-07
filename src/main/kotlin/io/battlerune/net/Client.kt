package io.battlerune.net

import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.out.InterfacePacketEncoder
import io.battlerune.net.packet.out.InterfaceTextPacketEncoder
import io.battlerune.net.packet.out.RegionUpdatePacketEncoder
import io.battlerune.net.packet.out.RootInterfacePacketEncoder

class Client(val player: Player) {

    fun setRootInterface(interfaceId: Int) : Client {
        player.write(RootInterfacePacketEncoder(interfaceId))
        return this
    }

    fun setInterface(rootInterfaceId: Int, childId: Int, interfaceId: Int, clickable: Boolean) : Client {
        player.write(InterfacePacketEncoder(rootInterfaceId, childId, interfaceId, clickable))
        return this
    }

    fun setInterfaceText(root: Int, child: Int, message: String) : Client {
        player.write(InterfaceTextPacketEncoder(root, child, message))
        return this
    }

    fun sendRegionUpdate(gpi: RSByteBufWriter) : Client {
        player.write(RegionUpdatePacketEncoder(gpi.buffer))
        return this
    }

}