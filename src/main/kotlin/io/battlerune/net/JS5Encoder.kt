package io.battlerune.net

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class JS5Encoder : MessageToByteEncoder<JS5FileRequest>() {

    override fun encode(ctx: ChannelHandlerContext, msg: JS5FileRequest, out: ByteBuf) {

        val index = msg.index
        val file = msg.file

        if (index == 255 && file == 255) {

        } else if (index == 255) {

        } else {

        }

        out.writeByte(index)
        out.writeShort(file)

    }

}