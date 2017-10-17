package io.battlerune.net

import io.battlerune.game.world.Position
import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.out.*

class Client(val player: Player) {

    fun itemChatbox(message: String, item: Int, zoom: Int = 1) {
        setInterface(162, 546, 193, false)
        setInterfaceText(193, 1, message)
        setInterfaceText(193, 2, "Click here to continue")
        player.write(ItemOnInterfacePacketEncoder(193, 0, item, zoom))
        setInterfaceSettings(193, 2, -1, -1, 1)
        setInterfaceSettings(193, 3, -1, -1, 0)
        setInterfaceSettings(193, 4, -1, -1, 0)
    }

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

    fun setRegionCoordinate(position: Position) : Client {
        player.write(SetRegionCoordintePacketEncoder(position))
        return this
    }

    fun showGroundItem(id: Int, amount: Int, position: Position) : Client {
        setRegionCoordinate(position)
        player.write(ShowGroundItemPacketEncoder(id, amount))
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

    fun setCamera(value1: Int, value2: Int) : Client {
        player.write(SetCameraPacketEncoder(value1, value2))
        return this
    }

    fun setDestination(position: Position) : Client {
        player.write(SetDestinationPacketEncoder(position))
        return this
    }

    fun sendCS2Script(id: Int, params: Array<Any>) : Client {
        player.write(CS2ScriptPacketEncoder(id, params))
        return this
    }

    fun lookupDNS(hostAddress: String) : Client {
        player.write(DNSLookupPacketEncoder(hostAddress))
        return this
    }

    fun sendRegionUpdate(buffer: RSByteBufWriter, flushPacket: Boolean) : Client {
        player.write(StaticRegionUpdatePacketEncoder(buffer), flushPacket)
        return this
    }

    fun removeInterface(interfaceId: Int) : Client {
        player.write(RemoveInterfacePacketEncoder(interfaceId))
        return this
    }

    fun setWeight(amount: Int) : Client {
        player.write(SetWeightPacketEncoder(amount))
        return this
    }

    fun playSong(songId: Int) : Client {
        player.write(PlaySongPacketEncoder(songId))
        return this
    }

    fun playSound(id: Int, type: Int, delay: Int) : Client {
        player.write(PlaySoundEffectPacketEncoder(id, type, delay))
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

    fun resetVarps() : Client {
        player.write(ResetVarpPacketEncoder())
        return this
    }

    fun npcUpdate() : Client {
        player.write(NpcUpdatePacketEncoder())
        return this
    }

    fun updatePlayer() : Client {
        if (!player.viewport.initialized) {
            return this
        }
        player.write(PlayerUpdatePacketEncoder())
        return this
    }

}