package io.battlerune.net

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class JS5Decoder : ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext, incoming: ByteBuf, outgoing: MutableList<Any>) {

        if (incoming.readableBytes() >= 4) {
            val type = incoming.readUnsignedByte().toInt()

            println("type $type")

            when(type) {

                0, 1 -> {

                    val fileType = incoming.readUnsignedByte().toInt()
                    val fileId = incoming.readUnsignedShort()



                }

                2, 3, 4 -> {
                    incoming.skipBytes(3)
                }

            }

        }

    }

}