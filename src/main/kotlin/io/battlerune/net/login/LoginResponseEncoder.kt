package io.battlerune.net.login

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class LoginResponseEncoder : MessageToByteEncoder<LoginResponse>() {

    override fun encode(ctx: ChannelHandlerContext, msg: LoginResponse, out: ByteBuf) {
        // successful
        out.writeByte(2)

        out.writeBoolean(false) // preference flag
        out.writeInt(0)
        out.writeByte(2) // rights
        out.writeBoolean(false) // flag
        out.writeShort(1) // index

        ctx.pipeline().remove(this)
    }

}