package io.battlerune.net.channel

import io.battlerune.net.packet.PacketRepository
import io.battlerune.game.world.actor.Player
import io.battlerune.net.NetworkConstants
import io.battlerune.net.codec.login.LoginRequest
import io.battlerune.net.packet.IncomingPacket
import io.battlerune.net.packet.PacketWriter
import io.netty.channel.Channel
import io.netty.channel.socket.SocketChannel
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class PlayerChannel(val channel: Channel) {

    val incomingPackets: Queue<IncomingPacket> = ConcurrentLinkedQueue()
    val prioritizedPackets: Queue<IncomingPacket> = ConcurrentLinkedQueue()

    val player = Player(this)
    val hostAddress: String = (channel as SocketChannel).remoteAddress().address.hostAddress

    fun handleLogin(loginRequest: LoginRequest) {
        val username = loginRequest.username
        val password = loginRequest.password
        val context = loginRequest.gameContext

        if (!username.matches("^[a-z0-9_ ]{1,12}$".toRegex()) || password.isEmpty() || password.length > 20) {
            return
        }

        player.username = username
        player.password = password
        player.context = context

        context.world.queueLogin(player)

        channel.writeAndFlush(loginRequest)
    }

    fun handlePrioritizedPackets() {
        while(true) {
            val packet = prioritizedPackets.poll() ?: break

            val reader = PacketRepository.readers[packet.opcode] ?: continue

            reader.readPacket(player, packet)
        }
    }

    fun handleQueuedPackets() {
        handlePrioritizedPackets()

        while(true) {
            val packet = incomingPackets.poll() ?: break

            val handler = PacketRepository.readers[packet.opcode] ?: continue

            handler.readPacket(player, packet)
        }

    }

    fun handleIncomingPacket(packet: IncomingPacket) {
        if (incomingPackets.size > NetworkConstants.PACKET_LIMIT) {
            return
        }

        if (packet.isPriority()) {
            prioritizedPackets.add(packet)
        } else {
            incomingPackets.add(packet)
        }
    }

    fun writeAndFlush(writer: PacketWriter) {
        val packet = writer.writePacket(player)

        packet.ifPresent { channel.writeAndFlush(it) }
    }

}