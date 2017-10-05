package io.battlerune.net.codec.update

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class UpdateDecoder : ByteToMessageDecoder() {

    companion object {
        val NORMAL_FILE_REQUEST = 0
        val PRIORITY_FILE_REQUEST = 1
        val CLIENT_LOGGED_IN = 2
        val CLIENT_LOGGED_OUT = 3
        val NEW_ENCRYPTION_USED = 4
    }

    override fun decode(ctx: ChannelHandlerContext, incoming: ByteBuf, outgoing: MutableList<Any>) {

        if (incoming.readableBytes() >= 4) {
            val type = incoming.readUnsignedByte().toInt()

            when(type) {

                NORMAL_FILE_REQUEST, PRIORITY_FILE_REQUEST -> {

                    val index = incoming.readUnsignedByte().toInt()
                    val file = incoming.readUnsignedShort()
                    val priority = type == 1

                    outgoing.add(FileRequest(index, file, priority))
                }

                CLIENT_LOGGED_IN, CLIENT_LOGGED_OUT, NEW_ENCRYPTION_USED -> {
                    incoming.skipBytes(3)
                }

            }

        }

    }

}