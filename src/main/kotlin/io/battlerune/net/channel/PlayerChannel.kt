package io.battlerune.net.channel

import io.battlerune.net.packet.PacketRepository
import io.battlerune.game.world.actor.Player
import io.battlerune.game.world.actor.PlayerContext
import io.battlerune.net.NetworkConstants
import io.battlerune.net.codec.game.RSByteBufReader
import io.battlerune.net.codec.login.LoginRequest
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.netty.channel.Channel
import io.netty.channel.socket.SocketChannel
import org.apache.logging.log4j.LogManager
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class PlayerChannel(val channel: Channel) {

    companion object {
        val logger = LogManager.getLogger()
    }

    val player = Player(this)

    val incomingPackets: Queue<Packet> = ConcurrentLinkedQueue()
    val prioritizedPackets: Queue<Packet> = ConcurrentLinkedQueue()

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

    private fun handlePrioritizedPackets() {
        if (prioritizedPackets.isEmpty()) {
            return
        }

        val packet = prioritizedPackets.poll() ?: return

        val decoder = PacketRepository.decoders[packet.opcode] ?: return

        val event = decoder.decode(player, RSByteBufReader.wrap(packet.payload))

        player.post(event)
    }

    fun handleQueuedPackets() {
        for (i in 0 until NetworkConstants.PACKET_LIMIT) {
            handlePrioritizedPackets()

            if (incomingPackets.isEmpty()) {
                break
            }

            val packet = incomingPackets.poll() ?: break

            val decoder = PacketRepository.decoders[packet.opcode] ?: continue

            val event = decoder.decode(player, RSByteBufReader.wrap(packet.payload))

            player.post(event)
        }
    }

    fun handleIncomingPacket(packet: Packet) {
        if (incomingPackets.size > NetworkConstants.PACKET_LIMIT) {
            return
        }

        if (packet.isPriority()) {
            prioritizedPackets.add(packet)
        } else {
            incomingPackets.add(packet)
        }

    }

    fun handleDownstreamPacket(encoder: PacketEncoder) {
        try {
            val packet = encoder.encode(player)

            channel.writeAndFlush(packet)
        } catch (ex: Throwable) {
            logger.warn("An exception was caught writing a packet.", ex)
        }
    }

}