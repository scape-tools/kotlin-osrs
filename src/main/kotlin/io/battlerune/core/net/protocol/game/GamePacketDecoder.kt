package io.battlerune.core.net.protocol.game

import io.battlerune.util.IsaacRandom
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class GamePacketDecoder(val isaacRandom: IsaacRandom) : ByteToMessageDecoder() {

    enum class State {
        OPCODE,

        SIZE,

        PAYLOAD
    }

    var state = State.OPCODE

    var opcode: Int = 0
    var size: Int = 0

    lateinit var packetType: PacketType

    val packetSizes = listOf(-2, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, -1, 0, 0, 2, 0, -2, -1, 0, 5, 0, 0, 0, -1, 0, 0, 0,
            0, 4, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, -2, -2, 0, -2, 0, 0, 6, 0, 0, 0, 0, -2, 0, 0, 0, 3, 10, 0, -1,
            -2, 0, -1, 0, 0, 0, 0, 0, 0, 0, -2, 10, 1, 0, 0, 0, 0, 0, 8, 0, 2, -1, 0, 0, -2, 0, 0, 0, 0, 0, 0, 0, 0,
            0, -2, 0, 0, 0, 0, 0, 4, 0, 6, 0, 0, 5, 0, 1, 0, 2, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 2, 4, 14, 1, 0, 0, 8,
            4, -2, 2, 0, 0, 0, 0, 0, 0, 2, -2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2, 0, -2, 5, 0, 0, 0, 0, 0,
            0, 6, 8, 0, 0, 0, 0, -2, 2, 12, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 4, 2, 0, 0, 3, 6, 0, 0, 0,
            0, 0, 0, 0, 6, 0, 6, 0, 0, -2, 0, 0, 0, 0, 0, -2, 0, 0, 2, 0, 7, 8, 20, 6, 0, 0, 0, 15, 0, -2, 0, 0, 6,
            6, 0, 0, 0, 0, 28, 0, 5, 0, 0, 0, 0, 6, 0, 0, 0, 0, -2, 0, 0, 0, 5, 0, 0, 0, 6, 0, 0, 0)

    override fun decode(ctx: ChannelHandlerContext, inc: ByteBuf, out: MutableList<Any>) {

        if (!inc.isReadable) {
            return
        }

        println("decoding packet state $state")

        when(state) {
            State.OPCODE -> {
                readOpcode(inc)
            }

            State.SIZE -> {
                readSize(inc)
            }

            State.PAYLOAD -> {
                readPayload(inc, out)
            }
        }

    }

    fun readOpcode(inc: ByteBuf) {
        if (!inc.isReadable) {
            return
        }

        opcode = (inc.readByte().toInt() - isaacRandom.nextInt()) and 0xFF

        size = packetSizes[opcode]

        println("opcode $opcode size $size")

        if (size == -2) {
            packetType = PacketType.VARIABLE_SHORT
        } else if (size == -1) {
            packetType = PacketType.VARIABLE_BYTE
        } else {
            packetType = PacketType.FIXED
        }

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
        out.add(GamePacket(opcode, packetType, inc.readBytes(size)))
        state = State.OPCODE
    }

    fun readSize(inc: ByteBuf) {
        if (packetType == PacketType.VARIABLE_BYTE) {
            size = inc.readUnsignedByte().toInt()
        } else if (packetType == PacketType.VARIABLE_SHORT) {
            if (inc.readableBytes() >= 2) {
                size = inc.readUnsignedShort()
            }
        }
        state = State.PAYLOAD
    }

}