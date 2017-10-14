package io.battlerune.net.channel

import io.battlerune.game.GameContext
import io.battlerune.net.packet.PacketRepository
import io.battlerune.game.world.actor.pawn.player.Player
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

class PlayerChannel(val channel: Channel, val context: GameContext) {

    companion object {
        val logger = LogManager.getLogger()
    }

    val player = Player(this, context)

    val incomingPackets: Queue<Packet> = ConcurrentLinkedQueue()
    val prioritizedPackets: Queue<Packet> = ConcurrentLinkedQueue()

    val hostAddress: String = (channel as SocketChannel).remoteAddress().address.hostAddress

    fun handleLogin(loginRequest: LoginRequest) {
        val username = loginRequest.username
        val password = loginRequest.password

        if (!username.matches("^[a-z0-9_ ]{1,12}$".toRegex()) || password.isEmpty() || password.length > 20) {
            return
        }

        player.username = username
        player.password = password

        context.world.queueLogin(player)

        channel.writeAndFlush(loginRequest)
    }

    private fun handlePrioritizedPackets() {
        if (prioritizedPackets.isEmpty()) {
            return
        }

        val packet = prioritizedPackets.poll() ?: return

        val decoder = PacketRepository.decoders[packet.opcode] ?: return

        val event = decoder.decode(player, RSByteBufReader.wrap(packet.payload)) ?: return

        player.post(event)
    }

    fun handleQueuedPackets() {
        try {
            for (i in 0 until NetworkConstants.PACKET_LIMIT) {
                handlePrioritizedPackets()

                if (incomingPackets.isEmpty()) {
                    break
                }

                val packet = incomingPackets.poll() ?: break

                val decoder = PacketRepository.decoders[packet.opcode] ?: continue

                val event = decoder.decode(player, RSByteBufReader.wrap(packet.payload)) ?: continue

                player.post(event)

            }
        } catch(ex: Throwable) {
            logger.error("An exception was caught while handling an incoming packet for player=${player.username}.", ex)
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

    fun handleDownstreamPacket(encoder: PacketEncoder, flushPacket: Boolean = false) {
        try {
            val packet = encoder.encode(player)

            if (flushPacket) {
                channel.writeAndFlush(packet)
            } else {
                channel.write(packet)
            }
        } catch (ex: Throwable) {
            logger.warn("An exception was caught writing a packet.", ex)
        }
    }

}