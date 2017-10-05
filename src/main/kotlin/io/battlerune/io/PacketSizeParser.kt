package io.battlerune.io

import com.google.gson.JsonObject
import io.battlerune.net.packet.PacketRepository
import io.battlerune.net.packet.PacketReader
import io.battlerune.util.GsonParser
import org.apache.logging.log4j.LogManager

class PacketSizeParser : GsonParser("./data/packet_sizes.json") {

    companion object {
        val logger = LogManager.getLogger()
    }

    override fun parse(data: JsonObject) {

        val opcode = data.get("opcode").asInt
        val size = data.get("size").asInt

        if (data.has("handler")) {

            val handler = Class.forName(data.get("handler").asString).newInstance()

            if (handler is PacketReader) {
                PacketRepository.readers[opcode] = handler
            }

        }

        PacketRepository.sizes[opcode] = size

    }

    override fun onComplete() {
        logger.info("Loaded: $count packet sizes.")
    }

}