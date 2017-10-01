package io.battlerune.net.codec.handshake

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class HandshakeEncoder : MessageToByteEncoder<HandshakeMessage>() {

    override fun encode(ctx: ChannelHandlerContext, msg: HandshakeMessage, out: ByteBuf) {
        out.writeByte(msg.value)

        ctx.pipeline().remove(this)
    }

}