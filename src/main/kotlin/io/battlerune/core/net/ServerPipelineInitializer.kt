package io.battlerune.core.net

import io.battlerune.core.net.codec.handshake.HandshakeDecoder
import io.battlerune.core.net.codec.handshake.HandshakeEncoder
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

@ChannelHandler.Sharable
class ServerPipelineInitializer : ChannelInitializer<SocketChannel>() {

    companion object {
        val channelHandler = UpstreamChannelHandler()
    }

    override fun initChannel(ch: SocketChannel) {
        ch.pipeline()
                .addLast(HandshakeDecoder::class.simpleName, HandshakeDecoder())
                .addLast(HandshakeEncoder::class.simpleName, HandshakeEncoder())
                .addLast(UpstreamChannelHandler::class.simpleName, channelHandler)
    }

}