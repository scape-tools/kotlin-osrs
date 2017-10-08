package io.battlerune.net.codec.login

import io.battlerune.game.GameContext
import io.battlerune.net.ProtocolConstants
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class LoginRequestDecoder(private val gameContext: GameContext) : ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext, inc: ByteBuf, out: MutableList<Any>) {

        if (!inc.isReadable || inc.readableBytes() < 8) {
            return
        }

        val loginType = inc.readUnsignedByte().toInt()

        val length = inc.readShort()

        if (length.toInt() != inc.readableBytes()) {
            return
        }

        val version = inc.readInt()

        if (version != ProtocolConstants.CLIENT_VERSION) {
            return
        }

        ctx.pipeline().replace(LoginRequestDecoder::class.simpleName, LoginDecoder::class.simpleName, LoginDecoder(gameContext))
        ctx.pipeline().addAfter(LoginDecoder::class.simpleName, LoginResponseEncoder::class.simpleName, LoginResponseEncoder())

    }

}