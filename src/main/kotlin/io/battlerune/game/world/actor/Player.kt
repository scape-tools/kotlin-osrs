package io.battlerune.game.world.actor

import io.battlerune.game.GameContext
import io.battlerune.net.channel.PlayerChannel
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.WritablePacket
import io.battlerune.net.packet.out.*

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

        writePacket(InterfaceTextOutgoingPacket(378, 13, "You last logged in <col=ff0000>earlier today<col=000000>."))
        writePacket(InterfaceTextOutgoingPacket(378, 14, "Never tell anyone your password, even if they claim to work for Jagex!"))
        writePacket(InterfaceTextOutgoingPacket(378, 15, "You have <col=00ff00>0 unread messages <col=ffff00>in your message centre."))
        writePacket(InterfaceTextOutgoingPacket(378, 15, "You have <col=00ff00>0 unread messages <col=ffff00>in your message centre."))
        writePacket(InterfaceTextOutgoingPacket(378, 16, "You do not have a Bank PIN. Please visit a bank if you would like one."))
        writePacket(InterfaceTextOutgoingPacket(378, 18, "You have <col=00ff00>UNLIMITED<col=ffff00> days of OldScape member credit remaining."))
        writePacket(InterfaceTextOutgoingPacket(378, 20, "A membership subscription grants access to the members-only features of RuneScape."))
        writePacket(InterfaceTextOutgoingPacket(378, 21, "Keep your account secure."))
        writePacket(InterfaceTextOutgoingPacket(50, 3, "The <col=6f0000>Deadman Invitational Tournament</col> is here!<br>Tune into the <col=0f0fff>Final</col> at <col=6f0000>4pm UTC on Saturday</col>!"))


        writePacket(RootInterfaceOutgoingPacket(165))
        writePacket(InterfaceOutgoingPacket(165, 1, 162, true))
        writePacket(InterfaceOutgoingPacket(165, 8, 593, true))
        writePacket(InterfaceOutgoingPacket(165, 9, 320, true))
        writePacket(InterfaceOutgoingPacket(165, 10, 76, true))
        writePacket(InterfaceOutgoingPacket(165, 11, 149, true))
        writePacket(InterfaceOutgoingPacket(165, 12, 387, true))
        writePacket(InterfaceOutgoingPacket(165, 13, 541, true))
        writePacket(InterfaceOutgoingPacket(165, 14, 218, true))
        writePacket(InterfaceOutgoingPacket(165, 15, 7, true))
        writePacket(InterfaceOutgoingPacket(165, 16, 429, true))
        writePacket(InterfaceOutgoingPacket(165, 17, 432, true))
        writePacket(InterfaceOutgoingPacket(165, 18, 182, true))
        writePacket(InterfaceOutgoingPacket(165, 19, 261, true))
        writePacket(InterfaceOutgoingPacket(165, 20, 216, true))
        writePacket(InterfaceOutgoingPacket(165, 21, 239, true))
        writePacket(InterfaceOutgoingPacket(165, 23, 163, true))
        writePacket(InterfaceOutgoingPacket(165, 24, 160, true))
        writePacket(InterfaceOutgoingPacket(165, 28, 50, false))
        writePacket(InterfaceOutgoingPacket(165, 29, 378, false))
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