package io.battlerune.game.world.actor

import io.battlerune.game.GameContext
import io.battlerune.net.channel.PlayerChannel
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.WritablePacket
import io.battlerune.net.packet.out.IPLookupOutgoingPacket
import io.battlerune.net.packet.out.RegionUpdateOutgoingPacket

class Player(val playerChannel: PlayerChannel) : Pawn() {

    lateinit var username: String
    lateinit var password: String
    lateinit var context: GameContext

    fun init() {

    }

    fun onLogin() {
        val builder = RSByteBufWriter.alloc()

        builder.switchToBitAccess()
        builder.writeBits(30, position.toPositionPacked())

        try {

            for (i in 1 until 2048) {

                if (i == this.index) {
                    continue
                }

                val player = context.world.players.list[i]

                if (player == null) {
                    builder.writeBits(18, 0)
                } else {
                    builder.writeBits(18, player.position.toRegionPacked())
                }

            }

        } catch (ex: Exception) {
            println(ex.message)
        }

        builder.switchToByteAccess()

        writePacket(RegionUpdateOutgoingPacket(builder))
        //writePacket(IPLookupOutgoingPacket())
    }

    fun logout() {
        context.world.queueLogout(this)
    }

    fun onLogout() {

    }

    fun writePacket(writer: WritablePacket) : Player {
        playerChannel.writeAndFlush(writer)
        return this
    }

}