package io.battlerune.net.codec.game

import io.battlerune.net.crypt.ISAACCipher
import io.battlerune.net.packet.IncomingPacket
import io.battlerune.net.packet.PacketRepository
import io.battlerune.net.packet.PacketType
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class PacketDecoder(val random: ISAACCipher) : ByteToMessageDecoder() {

    enum class State {
        OPCODE,

        SIZE,

        PAYLOAD
    }

    var state = State.OPCODE
    var opcode: Int = 0
    var size: Int = 0

    lateinit var packetType: PacketType

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

    fun readOpcode(inc: ByteBuf) {
        if (!inc.isReadable) {
            return
        }

        // TODO ISAAC
        opcode = inc.readUnsignedByte().toInt()
        size = PacketRepository.sizes[opcode]

        if (size == -2) {
            packetType = PacketType.VAR_SHORT
        } else if (size == -1) {
            packetType = PacketType.VAR_BYTE
        } else if (size == 0) {
            //println("Unknown packet: $opcode length: ${inc.readableBytes()}")
            return
        } else {
            packetType = PacketType.FIXED
        }

        //println("Known packet: $opcode type: $packetType")

        if (packetType == PacketType.FIXED) {
            state = State.PAYLOAD
        } else {
            state = State.SIZE
        }
    }

    fun readPayload(inc: ByteBuf, out: MutableList<Any>) {

        if (inc.readableBytes() < size) {
            return
        }

        if (size != 0) {
            out.add(IncomingPacket(opcode, packetType, inc.readBytes(size)))
        }
        state = State.OPCODE
    }

    fun readSize(inc: ByteBuf) {
        if (packetType == PacketType.VAR_BYTE) {
            size = inc.readUnsignedByte().toInt()
        } else if (packetType == PacketType.VAR_SHORT) {
            if (inc.readableBytes() >= 2) {
                size = inc.readUnsignedShort()
            }
        }
        state = State.PAYLOAD
    }

}