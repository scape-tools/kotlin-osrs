package io.battlerune.net.login

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class LoginStateEncoder : MessageToByteEncoder<LoginResponse>() {

    override fun encode(ctx: ChannelHandlerContext, msg: LoginResponse, out: ByteBuf) {

        println("reached login state encoder")

        // successful
        out.writeByte(2)

        out.writeByte(0)
        out.writeByte(0)
        out.writeByte(0)
        out.writeByte(0)
        out.writeByte(0)
        out.writeByte(2) // rights
        out.writeByte(0)
        out.writeShort(1) // player index
        out.writeByte(1)

        ctx.pipeline().remove(this)

    }

}