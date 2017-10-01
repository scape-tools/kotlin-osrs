package io.battlerune.net

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class JS5Encoder : MessageToByteEncoder<JS5FileRequest>() {

    override fun encode(ctx: ChannelHandlerContext, msg: JS5FileRequest, out: ByteBuf) {

    }

}