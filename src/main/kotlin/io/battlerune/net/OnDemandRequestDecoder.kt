package io.battlerune.net

import io.battlerune.core.Server
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class OnDemandRequestDecoder() : ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext, incoming: ByteBuf, outgoing: MutableList<Any>) {

        if (incoming.readableBytes() >= 4) {
            val type = incoming.readUnsignedByte().toInt()

            when (type) {

                0, 1 -> {

                    val fileStore = incoming.readUnsignedByte().toInt()
                    val file = incoming.readUnsignedByte().toInt()

                    val response = ctx.alloc().buffer()
                    response.writeByte(fileStore)
                            .writeShort(file)

                    if (fileStore == 0xFF && file == 0xFF) {

                    } else {

                        val buffer = Server.cache.store.read(fileStore, file)

                        val compression = buffer.get().toInt() and 0xFF
                        val length = buffer.int

                        response.writeByte(compression)
                        response.writeInt(length)

                        val payload = ByteArray((if (compression != 0) length - 4 else length))

                        System.arraycopy(buffer.array(), 5, payload, 0, payload.size)

                        var offset = 8

                        for (value in payload) {
                            if (offset == 512) {
                                response.writeByte(0xFF)
                                offset = 1
                            }
                            response.writeByte(value.toInt())
                            offset++
                        }

                    }

                    ctx.writeAndFlush(response, ctx.voidPromise())

                    println("fileStore $fileStore file $file")

                }

                2, 3, 4 -> {
                    incoming.skipBytes(3)
                }

            }

        }

    }

}