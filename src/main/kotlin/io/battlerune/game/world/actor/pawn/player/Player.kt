package io.battlerune.game.world.actor.pawn.player

import io.battlerune.game.GameContext
import io.battlerune.game.event.Event
import io.battlerune.game.widget.DisplayType
import io.battlerune.game.world.actor.pawn.ChatMessage
import io.battlerune.game.world.actor.pawn.Graphic
import io.battlerune.game.world.actor.pawn.Pawn
import io.battlerune.game.world.actor.pawn.update.UpdateFlag
import io.battlerune.net.Client
import io.battlerune.net.channel.PlayerChannel
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.out.LogoutPacketEncoder

class Player(val channel: PlayerChannel, val context: GameContext) : Pawn() {

    var appearance = Appearance.DEFAULT

    val viewport = Viewport(this)

    var chatMessage = ChatMessage()

    var rights = Rights.PLAYER

    var initialized = false
    var xpOverlay = false

    var displayType = DisplayType.FIXED

    var inGame = false

    var teleported = false

    val client = Client(this)

    lateinit var username: String
    lateinit var password: String

    fun chat(msg: String, color: Int = 0, effect: Int = 0) {
        if (msg.isEmpty())
            return

        chatMessage = ChatMessage(msg, color, effect)
        updateFlags.add(UpdateFlag.CHAT)
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
                .setCamera(0, 0)
                .lookupDNS("127.0.0.1")
                .setInterfaceText(378, 14, "Never tell anyone your password, even if they claim to work for Jagex!")
                .setInterfaceText(378, 15, "You have 0 unread messages in your message centre.")
                .setInterfaceText(378, 18, "You are not a member. Subscribe to access extra skills, areas and quests, and much<br>more besides.")
                .setInterfaceText(378, 20, "A membership subscription grants access to the members-only features of both versions of RuneScape.")
                .setInterfaceText(378, 21, "Keep your account secure.")
                .setInterfaceText(378, 13, "You last logged in <col=ff0000>earlier today<col=000000>.")
                .setInterfaceText(378, 16, "You do not have a Bank PIN. Please visit a bank if you would like one.")
                .setRootInterface(165)
                .setInterface(165, 1, 162, true)
                .setInterface(165, 23, 163, true)
                .setInterface(165, 24, 160, true)
                .setInterface(165, 29, 378, false)
                /**
                 * Login themes
                 *
                 * 16 toxic bomb
                 * 17 question marks
                 * 18 jester
                 * 19 vaults with red asterisks
                 * 20 vault with green asterisks
                 * 21 people trading
                 * 22 vaults with no marks
                 * 23 christmas themed
                 * 50 blank scroll
                 * 405 construction
                 *
                 */

                val loginTheme = 50

                client.setInterface(165, 28, loginTheme, false)
                .setInterfaceText(50, 3, "Once you've had a <col=cfcfcf>graceful set</col> repainted <col=2f2fff>blue</col> in <col=4f2f1f>Brimhaven</col>,<br>you can get <col=003600>individual pieces</col> repainted.<br>Next week, <col=9f005f>Halloween</col>!")
                .sendCS2Script(233, arrayOf(3276804, 7085, 0, 0, 434, 1912, 0, 400, -1))
                .sendCS2Script(233, arrayOf(3276805, 32817, 0, 100, 93, 179, 0, 800, 820))
                .sendCS2Script(1080, arrayOf())
                .setInterface(165, 9, 320, true)
                .setInterface(165, 10, 399, true)
                .setInterface(165, 11, 149, true)
                .setInterface(165, 12, 387, true)
                .setInterface(165, 13, 541, true)
                .setInterface(165, 14, 218, true)
                .setInterface(165, 16, 429, true)
                .setInterface(165, 17, 432, true)
                .setInterface(165, 18, 182, true)
                .setInterface(165, 19, 261, true)
                .setInterface(165, 20, 216, true)
                .setInterface(165, 21, 239, true)
                .setInterface(165, 15, 7, true)
                .setInterface(165, 8, 593, true)
                .setInterfaceText(593, 1, "Unarmed")
                .setInterfaceText(593, 2, "Combat Lvl: 126")
                .setInterfaceText(239, 5, "AUTO")
                .sendCS2Script(2014, arrayOf(0, 0, 0, 0, 0, 0))
                .sendCS2Script(2015, arrayOf(0))
                .setVarp(18, 1)
                .setVarp(20, 131072)
                .setVarp(21, 67141632)
                .setVarp(22, 33554432)
                .setVarp(23, 2097216)
                .setVarp(43, 1)
                .setVarp(101, 0)
                .setVarp(153, -1)
                .setVarp(166, 2)
                .setVarp(167, 0)
                .setVarp(168, 4)
                .setVarp(169, 4)
                .setVarp(170, 0)
                .setVarp(171, 0)
                .setVarp(173, 1)
                .setVarp(281, 1000)
                .setVarp(284, 60001)
                .setVarp(287, 0)
                .setVarp(300, 1000)
                .setVarp(406, 20)
                .setVarp(447, -1)
                .setVarp(449, 2097152)
                .setVarp(486, 1073741824)
                .setVarp(520, 1)
                .setVarp(553, -2147483648)
                .setVarp(788, 128)
                .setVarp(810, 33554432)
                .setVarp(849, -1)
                .setVarp(850, -1)
                .setVarp(851, -1)
                .setVarp(852, -1)
                .setVarp(853, -1)
                .setVarp(854, -1)
                .setVarp(855, -1)
                .setVarp(856, -1)
                .setVarp(872, 4)
                .setVarp(904, 253)
                .setVarp(913, 4194304)
                .setVarp(1010, 2048)
                .setVarp(1017, 8192)
                .setVarp(1050, 4096)
                .setVarp(1065, -1)
                .setVarp(1067, -1302855680)
                .setVarp(1074, 0)
                .setVarp(1075, -1)
                .setVarp(1107, 0)
                .setVarp(1151, -1)
                .setVarp(1224, 172395585)
                .setVarp(1225, 379887846)
                .setVarp(1226, 12)
                .setVarp(1306, 0)
                .setVarp(1427, -1)
                .sendMessage("Welcome to BattleRune #155!")
                .setEnergy(100)
                .playSong(1)

                 for (i in 0..24) {
                    client.setSkill(i, 99, 14_000_000)
                }

    }

    override fun preUpdate() {
        // handle packets
        channel.handleQueuedPackets()

        // TODO handle movement

        // TODO other processing before update occurs
    }

    override fun update() {
        client.updatePlayer()
    }

    override fun postUpdate() {
        updateFlags.clear()
        teleported = false
        regionChanged = false
    }

    override fun onMovement() {

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