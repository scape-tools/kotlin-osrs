package io.battlerune.net

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class HandshakeDecoder : ByteToMessageDecoder() {

    companion object {
        val LOGIN = 14
        val JS5_HANDSHAKE = 15
    }

    override fun decode(ctx: ChannelHandlerContext, incoming: ByteBuf, outgoing: MutableList<Any>) {

        if (incoming.readableBytes() < 5) {
            return
        }

        val handshake = incoming.readUnsignedByte().toInt()

        println("handshake $handshake")

        when(handshake) {
            LOGIN -> {

            }

            JS5_HANDSHAKE -> {
                val revision = incoming.readInt()

                println("revision $revision")

                val buf = ctx.alloc().buffer(1)

                if (revision == 149) {
                    buf.writeByte(HandshakeMessage.CONTINUE)
                } else {
                    buf.writeByte(HandshakeMessage.EXPIRED)
                }

                ctx.writeAndFlush(buf)

                ctx.pipeline().replace(HandshakeDecoder::class.simpleName, JS5Decoder::class.simpleName, JS5Decoder())
                ctx.pipeline().addAfter(JS5Decoder::class.simpleName, JS5Encoder::class.simpleName, JS5Encoder())

            }
        }

    }

}