package io.battlerune.game.world.actor

import io.battlerune.game.GameContext
import io.battlerune.net.channel.PlayerChannel
import io.battlerune.net.codec.game.AccessType
import io.battlerune.net.codec.game.GamePacketBuilder
import io.battlerune.net.packet.PacketWriter
import io.battlerune.net.packet.out.RegionUpdatePacket

class Player(val playerChannel: PlayerChannel) : Pawn() {

    lateinit var username: String
    lateinit var password: String
    lateinit var context: GameContext

    fun init() {

    }

    fun onLogin() {

        println("on login")

        val builder = GamePacketBuilder()
        builder.switchToBitAccess()

        builder.writeBits(30, position.toPositionPacked())

        for (player in context.world.players.list) {

            player ?: continue

            builder.writeBits(18, player.position.toRegionPacked())
        }

        builder.switchToByteAccess()

        writePacket(RegionUpdatePacket())

    }

    fun logout() {
        context.world.queueLogout(this)
    }

    fun onLogout() {

    }

    fun writePacket(writer: PacketWriter) : Player {
        playerChannel.writeAndFlush(writer)
        return this
    }

}