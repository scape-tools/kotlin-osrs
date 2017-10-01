package io.battlerune.net

import io.battlerune.net.codec.handshake.HandshakeDecoder
import io.battlerune.net.codec.handshake.HandshakeEncoder
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

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