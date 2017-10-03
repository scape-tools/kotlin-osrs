package io.battlerune.net.login

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class LoginRequestEncoder : MessageToByteEncoder<LoginRequest>() {

    override fun encode(ctx: ChannelHandlerContext, msg: LoginRequest, out: ByteBuf) {
        ctx.pipeline().replace(LoginRequestDecoder::class.simpleName, LoginStateDecoder::class.simpleName, LoginStateDecoder())
        ctx.pipeline().replace(LoginRequestEncoder::class.simpleName, LoginStateEncoder::class.simpleName, LoginStateDecoder())
    }

}