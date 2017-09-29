package io.battlerune.net

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class LoginRequestDecoder : ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext, inBuf: ByteBuf, Buf: MutableList<Any>) {

        val value = inBuf.readUnsignedByte()

        println("read $value")

    }

}