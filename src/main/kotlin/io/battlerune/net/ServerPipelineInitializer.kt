package io.battlerune.net

import io.battlerune.game.GameContext
import io.battlerune.net.codec.handshake.HandshakeDecoder
import io.battlerune.net.codec.handshake.HandshakeEncoder
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

@ChannelHandler.Sharable
class ServerPipelineInitializer(private val gameContext: GameContext) : ChannelInitializer<SocketChannel>() {

    companion object {
        val channelHandler = UpstreamChannelHandler()
    }

    override fun initChannel(ch: SocketChannel) {
        ch.pipeline()
                .addLast(HandshakeDecoder::class.simpleName, HandshakeDecoder())
                .addLast(HandshakeEncoder::class.simpleName, HandshakeEncoder(gameContext))
                .addLast(UpstreamChannelHandler::class.simpleName, channelHandler)
    }

}