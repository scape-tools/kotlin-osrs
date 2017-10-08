package io.battlerune.net.channel

import io.battlerune.net.NetworkConstants
import io.battlerune.net.codec.update.FileRequest
import io.battlerune.net.codec.handshake.HandshakeMessage
import io.battlerune.net.codec.login.LoginRequest
import io.battlerune.net.packet.Packet
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

@ChannelHandler.Sharable
class UpstreamChannelHandler : SimpleChannelInboundHandler<Any>() {

    override fun channelRead0(ctx: ChannelHandlerContext, msg: Any) {
            if (msg is HandshakeMessage) {
                ctx.writeAndFlush(msg)
            } else if (msg is FileRequest) {
                ctx.writeAndFlush(msg)
            } else if (msg is LoginRequest) {
                val playerChannel = ctx.channel().attr(NetworkConstants.SESSION_KEY).get() ?: return
                playerChannel.handleLogin(msg)
            } else if (msg is Packet) {
                val playerChannel = ctx.channel().attr(NetworkConstants.SESSION_KEY).get() ?: return
                playerChannel.handleIncomingPacket(msg)
            }
    }

    override fun channelInactive(ctx: ChannelHandlerContext) {
        super.channelInactive(ctx)
        val playerChannel = ctx.channel().attr(NetworkConstants.SESSION_KEY).get() ?: throw IllegalStateException("session is null")
        val player = playerChannel.player

        if (!player.initialized) {
            return
        }

        player.onLogout()
        player.context.world.queueLogout(player)
    }

}