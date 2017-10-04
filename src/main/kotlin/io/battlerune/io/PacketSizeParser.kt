package io.battlerune.io

import com.google.gson.JsonObject
import io.battlerune.net.packet.PacketHandlerRepository
import io.battlerune.net.packet.PacketReader
import io.battlerune.util.GsonParser

class PacketSizeParser : GsonParser("./data/packet_sizes.json") {

    override fun parse(data: JsonObject) {

        val opcode = data.get("opcode").asInt
        val size = data.get("size").asInt

        if (data.has("handler")) {

            val handler = Class.forName(data.get("handler").asString).newInstance()

            if (handler is PacketReader) {
                PacketHandlerRepository.readers[opcode] = handler
            }

        }

        PacketHandlerRepository.sizes[opcode] = size

    }

    override fun onComplete() {
        println("Loaded: $count packet sizes.")
    }

}