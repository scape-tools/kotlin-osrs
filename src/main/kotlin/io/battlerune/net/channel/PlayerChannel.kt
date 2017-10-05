package io.battlerune.net.channel

import io.battlerune.net.packet.GamePacket
import io.battlerune.net.packet.PacketRepository
import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.login.LoginRequest
import io.netty.channel.socket.SocketChannel
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class PlayerChannel(val loginRequest: LoginRequest) {

    val incomingPackets: Queue<GamePacket> = ConcurrentLinkedQueue()
    val prioritizedPackets: Queue<GamePacket> = ConcurrentLinkedQueue()

    val channel = loginRequest.channel
    val player: Player = Player(loginRequest.gameContext, loginRequest.username, loginRequest.password)
    val hostAddress: String = (channel as SocketChannel).remoteAddress().address.hostAddress

    fun validLogin() : Boolean {

        val username = loginRequest.username
        val password = loginRequest.password

        if (!username.matches("^[a-z0-9_ ]{1,12}$".toRegex()) || password.isEmpty() || password.length > 20) {
            return false
        }

        player.channel = this
        return true
    }

    fun handlePrioritizedPackets() {
        while(true) {
            val packet = prioritizedPackets.poll() ?: break

            val reader = PacketRepository.readers[packet.opcode] ?: continue

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