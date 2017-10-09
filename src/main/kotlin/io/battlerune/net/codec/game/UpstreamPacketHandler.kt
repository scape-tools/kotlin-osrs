package io.battlerune.net.codec.game

import io.battlerune.net.crypt.ISAACCipher
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketRepository
import io.battlerune.net.packet.PacketType
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class UpstreamPacketHandler(private val random: ISAACCipher) : ByteToMessageDecoder() {
    companion object {
        enum class State {
            OPCODE,
            SIZE,
            PAYLOAD
        }
    }

    var state = State.OPCODE
    var opcode: Int = 0
    var size: Int = 0
    var packetType: PacketType = PacketType.FIXED

    override fun decode(ctx: ChannelHandlerContext, inc: ByteBuf, out: MutableList<Any>) {
        if (!inc.isReadable) {
            return
        }

        when(state) {
            State.OPCODE -> { readOpcode(inc) }
            State.SIZE -> { readSize(inc) }
            State.PAYLOAD -> { readPayload(inc, out) }
        }
    }

    private fun readOpcode(inc: ByteBuf) {
        if (!inc.isReadable) {
            return
        }

        // TODO ISAAC
        opcode = inc.readUnsignedByte().toInt()
        size = PacketRepository.sizes[opcode]

        when (size) {
            -2 -> packetType =PacketType.VAR_SHORT
            -1 -> packetType =PacketType.VAR_BYTE
            0 -> return
            else -> packetType = PacketType.FIXED
        }

        state = (if (packetType == PacketType.FIXED) State.PAYLOAD else State.SIZE)
    }

    private fun readPayload(inc: ByteBuf, out: MutableList<Any>) {
        if (inc.readableBytes() < size) {
            return
        }

        if (size != 0) {
            out.add(Packet(opcode, packetType, inc.readBytes(size)))
        }
        state = State.OPCODE
    }

    private fun readSize(inc: ByteBuf) {
        if (packetType == PacketType.VAR_BYTE) {
            if (inc.isReadable) {
                size = inc.readUnsignedByte().toInt()
            }
        } else if (packetType == PacketType.VAR_SHORT) {
            if (inc.readableBytes() >= 2) {
                size = inc.readUnsignedShort()
            }
        }
        state = State.PAYLOAD
    }

}