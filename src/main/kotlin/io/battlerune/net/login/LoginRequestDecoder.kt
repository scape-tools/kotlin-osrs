package io.battlerune.net.login

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class LoginRequestDecoder : ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext, inc: ByteBuf, out: MutableList<Any>) {

        if (!inc.isReadable || inc.readableBytes() < 8) {
            return
        }

        val loginType = inc.readUnsignedByte().toInt()

        val length = inc.readShort()

        if (length.toInt() != inc.readableBytes()) {
            return
        }

        val version = inc.readInt()

        out.add(LoginRequest(loginType, version))
    }

}