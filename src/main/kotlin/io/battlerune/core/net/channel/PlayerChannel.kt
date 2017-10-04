package io.battlerune.core.net.channel

import io.battlerune.core.net.protocol.codec.game.GamePacket
import io.battlerune.core.net.packet.PacketHandlerRepository
import io.battlerune.game.world.actor.Player
import io.netty.channel.Channel
import io.netty.channel.socket.SocketChannel;
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class PlayerChannel(val channel: Channel) {

    val incomingPackets: Queue<GamePacket> = ConcurrentLinkedQueue()
    val prioritizedPackets: Queue<GamePacket> = ConcurrentLinkedQueue()

    val player: Player = Player(this)
    val hostAddress: String = (channel as SocketChannel).remoteAddress().address.hostAddress

    fun handlePrioritizedPackets() {
        while(true) {
            val packet = prioritizedPackets.poll() ?: break

            val reader = PacketHandlerRepository.map[packet.opcode] ?: continue

            reader.readPacket(player, packet)
        }
    }

    fun handleQueuedPackets() {

    }

    fun queue() {

    }

    fun flush() {
        this.channel.flush()
    }

}