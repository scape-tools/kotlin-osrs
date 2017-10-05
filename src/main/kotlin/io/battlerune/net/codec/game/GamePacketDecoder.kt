package io.battlerune.net.codec.game

import io.battlerune.net.crypt.IsaacRandom
import io.battlerune.net.packet.GamePacket
import io.battlerune.net.packet.PacketRepository
import io.battlerune.net.packet.PacketType
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

        size = PacketRepository.sizes[opcode]

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