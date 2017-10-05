package io.battlerune.net.codec.login

import io.battlerune.net.codec.game.GamePacketDecoder
import io.battlerune.net.codec.game.GamePacketEncoder
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class LoginResponseEncoder : MessageToByteEncoder<LoginRequest>() {

    override fun encode(ctx: ChannelHandlerContext, msg: LoginRequest, out: ByteBuf) {
        // successful
        out.writeByte(2)

        out.writeBoolean(false) // preference flag 1
        out.writeInt(0) // 5
        out.writeByte(2) // rights 6
        out.writeBoolean(true) // members 7
        out.writeShort(1) // index 9
        out.writeByte(1) // 10

        ctx.pipeline().replace(LoginDecoder::class.simpleName, GamePacketDecoder::class.simpleName, GamePacketDecoder(msg.isaacPair.decoder))
        ctx.pipeline().replace(LoginResponseEncoder::class.simpleName, GamePacketEncoder::class.simpleName, GamePacketEncoder(msg.isaacPair.encoder))
    }

}