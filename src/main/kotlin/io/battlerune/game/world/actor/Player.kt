package io.battlerune.game.world.actor

import io.battlerune.game.GameContext
import io.battlerune.game.event.Event
import io.battlerune.game.widget.DisplayType
import io.battlerune.game.world.World
import io.battlerune.net.Client
import io.battlerune.net.channel.PlayerChannel
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.out.LogoutPacketEncoder

class Player(val channel: PlayerChannel, val context: GameContext) : Pawn() {

    var appearance = Appearance.DEFAULT

    val viewport = Viewport(this)

    var initialized = false

    var displayType = DisplayType.FIXED

    var teleported = false

    val client = Client(this)

    lateinit var username: String
    lateinit var password: String

    override fun init() {
        regionChanged = true
    }

    fun post(event: Event) {
        context.world.eventBus.post(event)
    }

    fun onLogin() {
        updateFlags.add(UpdateFlag.APPEARANCE)
        region = context.regionManager.lookup(position.regionID)

        val gpiBuffer = RSByteBufWriter.alloc()
        viewport.initGPI(gpiBuffer)
        client.sendRegionUpdate(gpiBuffer, true)

                client.setInterfaceText(378, 13, "You last logged in <col=ff0000>earlier today<col=000000>.")
                .setInterfaceText(378, 14, "Never tell anyone your password, even if they claim to work for Jagex!")
                .setInterfaceText(378, 15, "You have <col=00ff00>0 unread messages <col=ffff00>in your message centre.")
                .setInterfaceText(378, 16, "You do not have a Bank PIN. Please visit a bank if you would like one.")
                .setInterfaceText(378, 18, "You have <col=00ff00>UNLIMITED<col=ffff00> days of OldScape member credit remaining.")
                .setInterfaceText(378, 20, "A membership subscription grants access to the members-only features of RuneScape.")
                .setInterfaceText(378, 21, "Keep your account secure.")
                .setInterfaceText(50, 3, "The <col=6f0000>Deadman Invitational Tournament</col> is here!<br>Tune into the <col=0f0fff>Final</col> at <col=6f0000>4pm UTC on Saturday</col>!")
                .setRootInterface(165)
                .setInterface(165, 1, 162, true)
                .setInterface(165, 8, 593, true)
                .setInterface(165, 9, 320, true)
                .setInterface(165, 10, 76, true)
                .setInterface(165, 11, 149, true)
                .setInterface(165, 12, 387, true)
                .setInterface(165, 13, 541, true)
                .setInterface(165, 14, 218, true)
                .setInterface(165, 15, 7, true)
                .setInterface(165, 16, 429, true)
                .setInterface(165, 17, 432, true)
                .setInterface(165, 18, 182, true)
                .setInterface(165, 19, 261, true)
                .setInterface(165, 20, 216, true)
                .setInterface(165, 21, 239, true)
                .setInterface(165, 23, 163, true)
                .setInterface(165, 24, 160, true)
                .setInterface(165, 28, 50, false)
                .setInterface(165, 29, 378, false)
                .setInterfaceText(593, 1, "Unarmed")
                .setInterfaceText(593, 2, "Combat Lvl: 126")
                .sendMessage("Welcome to BattleRune #155!")
                .setEnergy(100)

                 for (i in 0..24) {
                    client.setSkill(i, 99, 14_000_000)
                }

            client.playSong(1)

    }

    override fun preUpdate() {
        updateFlags.add(UpdateFlag.APPEARANCE)
    }

    override fun update() {
        client.updatePlayer()
    }

    override fun postUpdate() {
        updateFlags.clear()
        teleported = false
        regionChanged = false
    }

    fun onLogout() {

    }

    fun logout() {
        //TODO send logout packet

        context.world.queueLogout(this)

        write(LogoutPacketEncoder())
    }

    fun write(encoder: PacketEncoder, flushPacket: Boolean = true) : Player {
        channel.handleDownstreamPacket(encoder, flushPacket)
        return this
    }

}