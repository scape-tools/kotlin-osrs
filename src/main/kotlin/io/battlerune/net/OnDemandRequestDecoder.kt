package io.battlerune.net

import io.battlerune.core.Server
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class OnDemandRequestDecoder : ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext, incoming: ByteBuf, outgoing: MutableList<Any>) {

        if (incoming.readableBytes() >= 4) {
            val type = incoming.readUnsignedByte().toInt()

            when(type) {

                0, 1 -> {
                    val filestore = incoming.readUnsignedByte().toInt()
                    val file = incoming.readUnsignedByte().toInt()

                    val buf = Server.cache.store.read(filestore, file)

                    val compression = buf.get().toInt() and 0xFF
                    val length = buf.int

                    val response = ctx.alloc().buffer()

                    response.writeByte(filestore)
                            .writeInt(file)
                            .writeByte(compression)
                            .writeInt(length)


                    val bytes = ByteArray(length + 4)


                    println("filestore $filestore file $file compression $compression length $length")
                }

                2, 3 -> {
                    incoming.skipBytes(3)
                }

            }

        }

    }

}