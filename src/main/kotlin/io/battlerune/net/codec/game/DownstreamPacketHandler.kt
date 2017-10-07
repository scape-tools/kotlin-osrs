package io.battlerune.net.codec.game

import io.battlerune.net.crypt.ISAACCipher
import io.battlerune.net.packet.OutgoingPacket
import io.battlerune.net.packet.PacketType
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class DownstreamPacketHandler(val random: ISAACCipher) : MessageToByteEncoder<OutgoingPacket>() {

    override fun encode(ctx: ChannelHandlerContext, msg: OutgoingPacket, out: ByteBuf) {
        val type = msg.type
        val payload = msg.payload

        out.writeByte(msg.opcode + random.nextInt())

        if (type == PacketType.VAR_BYTE) {
            out.writeByte(payload.writerIndex())
        } else if (type == PacketType.VAR_SHORT) {
            out.writeShort(payload.writerIndex())
        }

        out.writeBytes(payload)
    }

}