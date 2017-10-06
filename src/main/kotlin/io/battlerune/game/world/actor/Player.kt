package io.battlerune.game.world.actor

import io.battlerune.game.GameContext
import io.battlerune.net.channel.PlayerChannel
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.WritablePacket
import io.battlerune.net.packet.out.RegionUpdatePacket

class Player(val playerChannel: PlayerChannel) : Pawn() {

    lateinit var username: String
    lateinit var password: String
    lateinit var context: GameContext

    fun init() {

    }

    fun onLogin() {

        println("on login")

        val builder = RSByteBufWriter()
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

    fun writePacket(writer: WritablePacket) : Player {
        println("writing packet")

        playerChannel.writeAndFlush(writer)
        println("wrote packet")
        return this
    }

}