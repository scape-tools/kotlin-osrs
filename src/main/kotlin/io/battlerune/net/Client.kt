package io.battlerune.net

import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.out.*

class Client(val player: Player) {

    fun setRootInterface(interfaceId: Int) : Client {
        player.write(RootInterfacePacketEncoder(interfaceId))
        return this
    }

    fun setInterface(rootInterfaceId: Int, childId: Int, interfaceId: Int, clickable: Boolean) : Client {
        player.write(InterfacePacketEncoder(rootInterfaceId, childId, interfaceId, clickable))
        return this
    }

    fun setInterfaceSets(fromRoot: Int, fromChild: Int, toRoot: Int, toChild: Int) : Client {
        player.write(InterfaceSetsPacketEncoder(fromRoot, fromChild, toRoot, toChild))
        return this
    }

    fun setInterfaceText(root: Int, child: Int, message: String) : Client {
        player.write(InterfaceTextPacketEncoder(root, child, message))
        return this
    }

    fun setInterfaceSettings(root: Int, component: Int, fromSlot: Int, toSlot: Int, setting: Int) : Client {
        player.write(InterfaceSettingPacketEncoder(root, component, fromSlot, toSlot, setting))
        return this
    }

    fun setVarp(id: Int, state: Int) : Client {
        player.write(VarpPacketEncoder(id, state))
        return this
    }

    fun sendRegionUpdate(gpi: RSByteBufWriter = RSByteBufWriter.alloc()) : Client {
        player.write(RegionUpdatePacketEncoder(gpi.buffer))
        return this
    }

    fun playSong(songId: Int) : Client {
        player.write(PlaySongPacketEncoder(songId))
        return this
    }

    fun sendMessage(message: String) : Client {
        player.write(ServerMessagePacketEncoder(message))
        return this
    }

    fun setSkill(skill: Int, lvl: Int, xp: Int) : Client {
        player.write(SetSkillPacketEncoder(skill, lvl, xp))
        return this
    }

    fun setEnergy(amount: Int) : Client {
        player.write(SetEnergyPacketEncoder(amount))
        return this
    }

    fun setSystemUpdate(seconds: Int) : Client {
        player.write(SystemUpdatePacketEncoder((600 / seconds) * 10))
        return this
    }

}