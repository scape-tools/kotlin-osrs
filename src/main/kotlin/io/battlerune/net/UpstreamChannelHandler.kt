package io.battlerune.net

import io.battlerune.net.codec.update.FileRequest
import io.battlerune.net.codec.handshake.HandshakeMessage
import io.battlerune.net.codec.login.LoginRequest
import io.battlerune.net.packet.IncomingPacket
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.apache.logging.log4j.LogManager
import java.util.*

@ChannelHandler.Sharable
class UpstreamChannelHandler : SimpleChannelInboundHandler<Any>() {

    val logger = LogManager.getLogger()

    override fun channelRead0(ctx: ChannelHandlerContext, msg: Any) {
        try {
            if (msg is HandshakeMessage) {
                ctx.writeAndFlush(msg)
            } else if (msg is FileRequest) {
                ctx.writeAndFlush(msg)
            } else if (msg is LoginRequest) {
                val playerChannel = ctx.channel().attr(NetworkConstants.SESSION_KEY).get() ?: return
                playerChannel.handleLogin(msg)
            } else if (msg is IncomingPacket) {
                val playerChannel = ctx.channel().attr(NetworkConstants.SESSION_KEY).get() ?: return
                playerChannel.handleIncomingPacket(msg)
            }
        } catch (ex: Exception) {
            if (NetworkConstants.IGNORED_EXCEPTIONS.none { Objects.equals(it, ex.message) }) {
                logger.warn("An error was detected upstream. ", ex)
            }
        }
    }

}