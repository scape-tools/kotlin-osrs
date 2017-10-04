package io.battlerune.net.codec.game

import io.battlerune.util.IsaacRandom
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class GamePacketDecoder(val isaacRandom: IsaacRandom) : ByteToMessageDecoder() {

    enum class State {
        READ_OPCODE,

        READ_SIZE,

        READ_PAYLOAD
    }

    var state = State.READ_OPCODE

    var opcode: Int = 0
    var size: Int = 0

    lateinit var packetType: PacketType

    val packetSizes = IntArray(256)

    val buttons = listOf(1, 7, 105, 142, 146, 171, 173, 196, 221, 250)

    init {
        for (i in 0 until packetSizes.size) {
            packetSizes[i] = -3
        }

        for (i in 0 until buttons.size) {
            packetSizes[buttons[i]] = 8
        }

        packetSizes[177] = 4
        packetSizes[64] = -1
        packetSizes[22] = -2
        packetSizes[26] = 6
        packetSizes[80] = 0
        packetSizes[208] = -1
        packetSizes[229] = 5
    }

    override fun decode(ctx: ChannelHandlerContext, inc: ByteBuf, out: MutableList<Any>) {

        if (state == State.READ_OPCODE) {
            if (!inc.isReadable) {
                return
            }

            opcode = inc.readByte().toInt() and 0xFF
            size = packetSizes[opcode]

            println("opcode $opcode size $size")

            if (size == -3) {
                packetType = PacketType.UNIDENTIFIED
            } else if (size == -2) {
                packetType = PacketType.VARIABLE_SHORT
            } else if (size == -1) {
                packetType = PacketType.VARIABLE_BYTE
            } else {
                packetType = PacketType.FIXED
            }

            state = (if (packetType != PacketType.FIXED) State.READ_SIZE else State.READ_PAYLOAD)

            if (state == State.READ_SIZE) {
                if (!inc.isReadable) {
                    return
                }

                if (packetType == PacketType.UNIDENTIFIED) {
                    size = inc.readableBytes()
                } else if (packetType == PacketType.VARIABLE_BYTE) {
                    size = inc.readUnsignedByte().toInt()
                } else if (packetType == PacketType.VARIABLE_SHORT) {
                    if (inc.readableBytes() >= 2) {
                        size = inc.readUnsignedShort()
                    }
                }

                state = State.READ_PAYLOAD

                if (state == State.READ_PAYLOAD) {
                    if (inc.readableBytes() < size) {
                        return
                    }

                    val payload = inc.readBytes(size)

                    state = State.READ_OPCODE

                    out.add(GamePacket(opcode, packetType, payload))
                }

            }

        }

    }

}