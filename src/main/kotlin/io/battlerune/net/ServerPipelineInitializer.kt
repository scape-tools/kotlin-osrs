package io.battlerune.net

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.timeout.IdleStateHandler

class ServerPipelineInitializer : ChannelInitializer<SocketChannel>() {

    override fun initChannel(ch: SocketChannel) {
        val pl = ch.pipeline()

        pl.addLast("decoder", LoginRequestDecoder())
                .addLast("encoder", LoginRequestEncoder())
                .addLast("timeout", IdleStateHandler(5, 0, 0))

    }

}