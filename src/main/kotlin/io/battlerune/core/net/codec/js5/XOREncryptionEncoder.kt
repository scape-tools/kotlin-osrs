package io.battlerune.core.net.codec.js5

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class XOREncryptionEncoder : MessageToByteEncoder<XOREncryptionResponse>() {

    override fun encode(ctx: ChannelHandlerContext, msg: XOREncryptionResponse, out: ByteBuf) {
        if (msg.key != 0) {
            for (i in 0 until out.writerIndex()) {
                out.setByte(i, out.getByte(i).toInt() xor msg.key)
            }
        }

        ctx.pipeline().remove(this)
    }
}