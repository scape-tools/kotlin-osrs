package io.battlerune.net.codec.handshake

import io.battlerune.net.codec.update.UpdateHandshakeMessage
import io.battlerune.net.codec.login.LoginHandshakeMessage
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class HandshakeDecoder : ByteToMessageDecoder() {

    companion object {
        val LOGIN_REQUEST_HANDSHAKE = 14
        val UPDATE_REQUEST_HANDSHAKE = 15
    }

    override fun decode(ctx: ChannelHandlerContext, incoming: ByteBuf, outgoing: MutableList<Any>) {

        if (!incoming.isReadable) {
            return
        }

        val requestType = incoming.readUnsignedByte().toInt()

        when(requestType) {
            LOGIN_REQUEST_HANDSHAKE -> {
                outgoing.add(LoginHandshakeMessage(requestType, HandshakeMessage.VERSION_CURRENT))
            }

            UPDATE_REQUEST_HANDSHAKE -> {
                val revision = incoming.readInt()

                outgoing.add(UpdateHandshakeMessage(requestType, HandshakeMessage.VERSION_CURRENT, revision))
            }
        }

    }

}