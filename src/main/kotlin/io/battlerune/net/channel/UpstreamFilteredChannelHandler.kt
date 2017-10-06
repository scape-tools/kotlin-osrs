package io.battlerune.net.channel

import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

@ChannelHandler.Sharable
class UpstreamFilteredChannelHandler : ChannelInboundHandlerAdapter() {

    override fun channelRegistered(ctx: ChannelHandlerContext) {
        // TODO implement filtering for now just forward to next handler
        ctx.fireChannelRegistered()
    }

    override fun channelUnregistered(ctx: ChannelHandlerContext) {
        // TODO implement filtering for now just forward to next handler
        ctx.fireChannelUnregistered()
    }

}