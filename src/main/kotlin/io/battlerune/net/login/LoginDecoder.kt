package io.battlerune.net.login

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class LoginDecoder : ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext, inc: ByteBuf, out: MutableList<Any>) {

        val opcode = inc.readUnsignedByte()

        println("opcode $opcode")

    }

}