package io.battlerune.net

import io.battlerune.game.GameContext
import io.battlerune.net.channel.ExceptionChannelHandler
import io.battlerune.net.channel.UpstreamFilteredChannelHandler
import io.battlerune.net.channel.PlayerChannel
import io.battlerune.net.channel.UpstreamChannelHandler
import io.battlerune.net.codec.handshake.HandshakeDecoder
import io.battlerune.net.codec.handshake.HandshakeEncoder
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.timeout.IdleStateHandler

@ChannelHandler.Sharable
class ServerPipelineInitializer(private val gameContext: GameContext) : ChannelInitializer<SocketChannel>() {

    companion object {
        val FILTER = UpstreamFilteredChannelHandler()
        val HANDLER = UpstreamChannelHandler()
    }

    override fun initChannel(ch: SocketChannel) {
        ch.attr(NetworkConstants.SESSION_KEY).setIfAbsent(PlayerChannel(ch, gameContext))
        ch.pipeline()
                .addLast(UpstreamFilteredChannelHandler::class.simpleName, FILTER)
                .addLast(HandshakeDecoder::class.simpleName, HandshakeDecoder())
                .addLast(HandshakeEncoder::class.simpleName, HandshakeEncoder())
                .addLast(IdleStateHandler(NetworkConstants.CONNECTION_TIMEOUT, 0, 0))
                .addLast(UpstreamChannelHandler::class.simpleName, HANDLER)
                .addLast(ExceptionChannelHandler::class.simpleName, ExceptionChannelHandler())
    }

}