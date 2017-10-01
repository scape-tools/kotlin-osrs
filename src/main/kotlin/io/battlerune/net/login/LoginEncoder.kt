package io.battlerune.net.login

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class LoginEncoder : MessageToByteEncoder<LoginRequest>() {

    override fun encode(ctx: ChannelHandlerContext, msg: LoginRequest, out: ByteBuf) {

    }

}