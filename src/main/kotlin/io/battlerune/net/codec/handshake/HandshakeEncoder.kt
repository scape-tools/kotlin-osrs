package io.battlerune.net.codec.handshake

import io.battlerune.game.GameContext
import io.battlerune.net.ProtocolConstants
import io.battlerune.net.codec.update.UpdateDecoder
import io.battlerune.net.codec.update.UpdateEncoder
import io.battlerune.net.codec.update.UpdateHandshakeMessage
import io.battlerune.net.codec.login.LoginRequestDecoder
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class HandshakeEncoder(private val gameContext: GameContext) : MessageToByteEncoder<HandshakeMessage>() {

    override fun encode(ctx: ChannelHandlerContext, msg: HandshakeMessage, out: ByteBuf) {
        // get past login stage 3
        out.writeByte(msg.response)

        if (msg is UpdateHandshakeMessage) {
            if (msg.version == ProtocolConstants.CLIENT_VERSION) {
                 ctx.pipeline().replace(HandshakeDecoder::class.simpleName, UpdateDecoder::class.simpleName, UpdateDecoder())
                ctx.pipeline().addAfter(UpdateDecoder::class.simpleName, UpdateEncoder::class.simpleName, UpdateEncoder(gameContext))
            }
        } else {

            // get past login stage 4
            out.writeLong((Math.random() * Long.MAX_VALUE).toLong())
            ctx.pipeline().replace(HandshakeDecoder::class.simpleName, LoginRequestDecoder::class.simpleName, LoginRequestDecoder(gameContext))
        }

        ctx.pipeline().remove(this)
    }

}