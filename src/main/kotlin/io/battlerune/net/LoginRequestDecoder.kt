package io.battlerune.net

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class LoginRequestDecoder : ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext, incoming: ByteBuf, outgoing: MutableList<Any>) {

        if (incoming.readableBytes() < 5) {
            return
        }

        val connectionType = incoming.readUnsignedByte().toInt()

        if (connectionType == 14) {

        } else if (connectionType == 15) {
            val revision = incoming.readInt()

            if (revision == 149) {
                ctx.writeAndFlush(ctx.alloc().buffer(1).writeByte(0), ctx.voidPromise())
            } else {
                ctx.writeAndFlush(ctx.alloc().buffer(1).writeByte(6)).addListener { ChannelFutureListener.CLOSE }
            }

            ctx.pipeline().replace("decoder", "decoder", OnDemandRequestDecoder())

        }

    }

}