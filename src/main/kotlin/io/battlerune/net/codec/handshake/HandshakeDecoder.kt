package io.battlerune.net.codec.handshake

import io.battlerune.net.codec.js5.JS5Decoder
import io.battlerune.net.codec.js5.JS5Encoder
import io.battlerune.net.codec.js5.XOREncryptionEncoder
import io.battlerune.net.login.LoginDecoder
import io.battlerune.net.login.LoginEncoder
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class HandshakeDecoder : ByteToMessageDecoder() {

    companion object {
        val LOGIN_HANDSHAKE = 14
        val JS5_HANDSHAKE = 15
    }

    override fun decode(ctx: ChannelHandlerContext, incoming: ByteBuf, outgoing: MutableList<Any>) {

        if (!incoming.isReadable) {
            return
        }

        val handshake = incoming.readUnsignedByte().toInt()

        println("handshake $handshake")

        when(handshake) {
            LOGIN_HANDSHAKE -> {
                ctx.pipeline().addAfter(HandshakeDecoder::class.simpleName, LoginDecoder::class.simpleName, LoginDecoder())
                ctx.pipeline().addAfter(LoginDecoder::class.simpleName, LoginEncoder::class.simpleName, LoginEncoder())
            }

            JS5_HANDSHAKE -> {
                val revision = incoming.readInt()

                println("revision $revision")

                if (revision == 149) {
                    outgoing.add(HandshakeMessage(HandshakeMessage.VERSION_CURRENT))
                    ctx.pipeline().replace(HandshakeDecoder::class.simpleName, XOREncryptionEncoder::class.simpleName, XOREncryptionEncoder())
                    ctx.pipeline().addAfter(XOREncryptionEncoder::class.simpleName, JS5Decoder::class.simpleName, JS5Decoder())
                    ctx.pipeline().addAfter(JS5Decoder::class.simpleName, JS5Encoder::class.simpleName, JS5Encoder())
                } else {
                    outgoing.add(HandshakeMessage(HandshakeMessage.VERSION_EXPIRED))
                }

            }
        }

    }

}