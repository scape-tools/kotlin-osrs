package io.battlerune.core.net.protocol.codec.handshake

import io.battlerune.core.net.protocol.codec.js5.JS5HandshakeMessage
import io.battlerune.core.net.protocol.codec.login.LoginHandshakeMessage
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

        val handshakeType = incoming.readUnsignedByte().toInt()

        println("handshake $handshakeType")

        when(handshakeType) {
            LOGIN_HANDSHAKE -> {
                outgoing.add(LoginHandshakeMessage(handshakeType, HandshakeMessage.VERSION_CURRENT))
            }

            JS5_HANDSHAKE -> {
                val revision = incoming.readInt()

                outgoing.add(JS5HandshakeMessage(handshakeType, HandshakeMessage.VERSION_CURRENT, revision))
            }
        }

    }

}