package io.battlerune.net

import io.battlerune.protocol.ConnectionType
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class LoginRequestEncoder : MessageToByteEncoder<ConnectionType>() {

    override fun encode(ctx: ChannelHandlerContext, msg: ConnectionType, out: ByteBuf) {

        println("reached encoder")

        if (msg.opcode == ConnectionType.GAME_SERVER_OPCODE) {
            println("Connection type: Game server")
        } else if (msg.opcode == ConnectionType.FILE_SERVER_OPCODE) {
            println("Connection type: File server")
        } else {
            println("Unrecognized connection type ${msg.opcode}.")
        }

    }

}