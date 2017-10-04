package io.battlerune.core.net

import io.battlerune.core.net.protocol.codec.game.GamePacket
import io.battlerune.core.net.protocol.codec.js5.JS5FileRequest
import io.battlerune.core.net.protocol.codec.handshake.HandshakeMessage
import io.battlerune.core.net.protocol.codec.login.LoginResponse
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

@ChannelHandler.Sharable
class UpstreamChannelHandler : SimpleChannelInboundHandler<Any>() {

    override fun channelRead0(ctx: ChannelHandlerContext, msg: Any) {
        if (msg is HandshakeMessage) {
            ctx.writeAndFlush(msg)
        } else if (msg is JS5FileRequest) {
            ctx.writeAndFlush(msg)
        } else if (msg is LoginResponse) {
            ctx.writeAndFlush(msg)
        } else if (msg is GamePacket) {
            ctx.writeAndFlush(msg)
        }
    }

}