package io.battlerune.net.codec.handshake

import io.battlerune.net.codec.js5.JS5Decoder
import io.battlerune.net.codec.js5.JS5Encoder
import io.battlerune.net.codec.js5.JS5HandshakeMessage
import io.battlerune.net.codec.js5.XOREncryptionEncoder
import io.battlerune.net.login.LoginDecoder
import io.battlerune.net.login.LoginEncoder
import io.battlerune.net.login.LoginHandshakeMessage
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class HandshakeEncoder : MessageToByteEncoder<HandshakeMessage>() {

    override fun encode(ctx: ChannelHandlerContext, msg: HandshakeMessage, out: ByteBuf) {
        out.writeByte(msg.response)

        if (msg is JS5HandshakeMessage) {
            if (msg.version == 149) {
                ctx.pipeline().replace(HandshakeDecoder::class.simpleName, XOREncryptionEncoder::class.simpleName, XOREncryptionEncoder())
                ctx.pipeline().addAfter(XOREncryptionEncoder::class.simpleName, JS5Decoder::class.simpleName, JS5Decoder())
                ctx.pipeline().addAfter(JS5Decoder::class.simpleName, JS5Encoder::class.simpleName, JS5Encoder())
            }
        } else {
            ctx.pipeline().replace(HandshakeDecoder::class.simpleName, LoginDecoder::class.simpleName, LoginDecoder())
            ctx.pipeline().addAfter(LoginDecoder::class.simpleName, LoginEncoder::class.simpleName, LoginEncoder())
        }
        ctx.pipeline().remove(this)
    }

}