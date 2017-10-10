package io.battlerune.net.codec.update

import io.battlerune.net.NetworkConstants
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class UpdateEncoder: MessageToByteEncoder<FileRequest>() {

    override fun encode(ctx: ChannelHandlerContext, msg: FileRequest, out: ByteBuf) {
        val index = msg.index
        val file = msg.file

        val response = ctx.alloc().buffer()
        response.writeByte(index)
                .writeShort(file)

        if (index == 255 && file == 255) {

            val playerChannel = ctx.channel().attr(NetworkConstants.SESSION_KEY).get()
            val checksums = playerChannel.context.checksumTable

            response.writeByte(0)
                    .writeInt(checksums.limit())
                    .writeBytes(checksums)

        } else if (index == 255) {

        } else {

        }

        ctx.writeAndFlush(response)
    }

}