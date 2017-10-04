package io.battlerune.core.net.codec.handshake

import io.battlerune.core.net.codec.js5.JS5Decoder
import io.battlerune.core.net.codec.js5.JS5Encoder
import io.battlerune.core.net.codec.js5.JS5HandshakeMessage
import io.battlerune.core.net.codec.js5.XOREncryptionEncoder
import io.battlerune.core.net.login.LoginRequestDecoder
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class HandshakeEncoder : MessageToByteEncoder<HandshakeMessage>() {

    override fun encode(ctx: ChannelHandlerContext, msg: HandshakeMessage, out: ByteBuf) {
        // get past login stage 3
        out.writeByte(msg.response)

        if (msg is JS5HandshakeMessage) {
            if (msg.version == 149) {
                ctx.pipeline().replace(HandshakeDecoder::class.simpleName, XOREncryptionEncoder::class.simpleName, XOREncryptionEncoder())
                ctx.pipeline().addAfter(XOREncryptionEncoder::class.simpleName, JS5Decoder::class.simpleName, JS5Decoder())
                ctx.pipeline().addAfter(JS5Decoder::class.simpleName, JS5Encoder::class.simpleName, JS5Encoder())
            }
        } else {

            // get past login stage 4
            out.writeLong((Math.random() * Long.MAX_VALUE).toLong())
            ctx.pipeline().replace(HandshakeDecoder::class.simpleName, LoginRequestDecoder::class.simpleName, LoginRequestDecoder())
        }

        ctx.pipeline().remove(this)
    }

}