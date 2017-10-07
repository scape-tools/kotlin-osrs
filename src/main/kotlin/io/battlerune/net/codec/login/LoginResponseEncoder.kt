package io.battlerune.net.codec.login

import io.battlerune.net.codec.game.UpstreamPacketHandler
import io.battlerune.net.codec.game.DownstreamPacketHandler
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

        ctx.pipeline().replace(LoginDecoder::class.simpleName, UpstreamPacketHandler::class.simpleName, UpstreamPacketHandler(msg.isaacPair.decoder))
        ctx.pipeline().replace(LoginResponseEncoder::class.simpleName, DownstreamPacketHandler::class.simpleName, DownstreamPacketHandler(msg.isaacPair.encoder))
    }

}