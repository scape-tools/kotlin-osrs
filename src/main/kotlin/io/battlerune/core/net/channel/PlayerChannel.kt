package io.battlerune.core.net.channel

import io.battlerune.game.widget.world.actor.Player
import io.netty.channel.Channel
import io.netty.channel.socket.SocketChannel;

class PlayerChannel(val channel: Channel) {

    val player: Player = Player(this)
    val hostAddress: String = (channel as SocketChannel).remoteAddress().address.hostAddress

    fun flush() {
        this.channel.flush()
    }

}