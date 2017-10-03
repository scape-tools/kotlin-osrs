package io.battlerune.net.login

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class LoginStateEncoder : MessageToByteEncoder<LoginResponse>() {

    override fun encode(ctx: ChannelHandlerContext, msg: LoginResponse, out: ByteBuf) {

        // successful
        out.writeByte(2)

        out.writeBoolean(false)

        out.writeByte(0)
        out.writeByte(0)
        out.writeByte(0)
        out.writeByte(0)

        out.writeByte(2) // rights

        out.writeBoolean(false)

        out.writeShort(1) // index

        out.writeByte(1)

        ctx.pipeline().remove(this)

    }

}