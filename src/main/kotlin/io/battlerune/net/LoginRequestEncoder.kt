package io.battlerune.net

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class LoginRequestEncoder : MessageToByteEncoder<Int>() {

    override fun encode(ctx: ChannelHandlerContext, msg: Int, out: ByteBuf) {

    }

}