package io.battlerune.net

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

class ServerPipelineInitializer : ChannelInitializer<SocketChannel>() {

    companion object {
        val channelHandler = UpstreamChannelHandler()
    }

    override fun initChannel(ch: SocketChannel) {
        ch.pipeline()
                .addLast(HandshakeDecoder::class.simpleName, HandshakeDecoder())
                .addLast(UpstreamChannelHandler::class.simpleName, channelHandler)
    }

}