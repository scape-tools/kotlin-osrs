package io.battlerune.net

import io.battlerune.net.channel.PlayerChannel
import io.battlerune.net.packet.GamePacket
import io.battlerune.net.codec.update.FileRequest
import io.battlerune.net.codec.handshake.HandshakeMessage
import io.battlerune.net.codec.login.LoginRequest
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
            val playerChannel = PlayerChannel(msg)

            if (playerChannel.validLogin()) {
                ctx.writeAndFlush(msg)
            }

        } else if (msg is GamePacket) {
            ctx.writeAndFlush(msg)
        }
    }

}