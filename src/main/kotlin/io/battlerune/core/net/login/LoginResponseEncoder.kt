package io.battlerune.core.net.login

import io.battlerune.core.net.codec.game.GamePacketDecoder
import io.battlerune.core.net.codec.game.GamePacketEncoder
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
        out.writeBoolean(true) // members
        out.writeShort(1) // index
        out.writeByte(1)

        ctx.pipeline().replace(LoginStateDecoder::class.simpleName, GamePacketDecoder::class.simpleName, GamePacketDecoder(msg.isaacPair.decoder))
        ctx.pipeline().replace(LoginResponseEncoder::class.simpleName, GamePacketEncoder::class.simpleName, GamePacketEncoder(msg.isaacPair.encoder))
    }

}