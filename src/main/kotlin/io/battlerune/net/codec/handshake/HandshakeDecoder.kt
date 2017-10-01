package io.battlerune.net.codec.handshake

import io.battlerune.net.codec.js5.JS5Decoder
import io.battlerune.net.codec.js5.JS5Encoder
import io.netty.buffer.ByteBuf
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

                if (revision == 149) {
                    outgoing.add(HandshakeMessage(HandshakeMessage.CONTINUE))
                } else {
                    outgoing.add(HandshakeMessage(HandshakeMessage.EXPIRED))
                }

                ctx.pipeline().replace(HandshakeDecoder::class.simpleName, JS5Decoder::class.simpleName, JS5Decoder())
                ctx.pipeline().addAfter(JS5Decoder::class.simpleName, JS5Encoder::class.simpleName, JS5Encoder())

            }
        }

    }

}