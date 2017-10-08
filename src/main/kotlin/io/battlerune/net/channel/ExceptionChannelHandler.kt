package io.battlerune.net.channel

import io.battlerune.net.NetworkConstants
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import org.apache.logging.log4j.LogManager

@ChannelHandler.Sharable
class ExceptionChannelHandler : ChannelInboundHandlerAdapter() {

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        if (!NetworkConstants.IGNORED_EXCEPTIONS.any { cause.message.equals(it) }) {
            cause.printStackTrace()
        }
    }

}