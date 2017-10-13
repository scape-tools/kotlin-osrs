package io.battlerune.net.packet.out

import io.battlerune.game.world.actor.Player
import io.battlerune.net.codec.game.RSByteBufWriter
import io.battlerune.net.packet.Packet
import io.battlerune.net.packet.PacketEncoder
import io.battlerune.net.packet.PacketType

class CS2ScriptPacketEncoder(val id: Int, val params: Array<Any>) : PacketEncoder {

    override fun encode(player: Player): Packet {
        val writer = RSByteBufWriter.alloc()

        var paramString = ""

        for (param in params.indices.reversed()) {
            if (params[param] is String) {
                paramString += "s"
            } else {
                paramString += "i"
            }
        }

        var parameter = 0
        for (index in params.size - 1 downTo 0) {
            if (paramString[index] == 's') {
                writer.writeString(params[parameter++] as String)
            } else {
                val param = params[parameter++]
                if (param is Int) {
                    writer.writeInt(param.toString().toInt())
                } else {
                    writer.writeString(param as String)
                }
            }
        }

        return writer.toPacket(144, PacketType.VAR_SHORT)
    }

}