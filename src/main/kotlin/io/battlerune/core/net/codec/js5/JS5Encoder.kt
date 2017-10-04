package io.battlerune.core.net.codec.js5

import io.battlerune.core.Server
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class JS5Encoder : MessageToByteEncoder<JS5FileRequest>() {

    override fun encode(ctx: ChannelHandlerContext, msg: JS5FileRequest, out: ByteBuf) {
        val index = msg.index
        val file = msg.file

        val response = ctx.alloc().buffer()
        response.writeByte(index)
                .writeShort(file)

        if (index == 255 && file == 255) {
            val checksums = Server.checksumTable.duplicate()

            response.writeByte(0)
                    .writeInt(checksums.limit())
                    .writeBytes(checksums)

        } else if (index == 255) {

        } else {

        }

        ctx.writeAndFlush(response)
    }

}