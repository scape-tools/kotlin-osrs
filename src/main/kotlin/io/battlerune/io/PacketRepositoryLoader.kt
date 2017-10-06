package io.battlerune.io

import com.google.gson.JsonObject
import io.battlerune.net.packet.PacketRepository
import io.battlerune.net.packet.ReadablePacket
import io.battlerune.util.GsonParser
import org.apache.logging.log4j.LogManager

class PacketRepositoryLoader : GsonParser("./data/packet_repository.json") {

    companion object {
        val logger = LogManager.getLogger()
    }

    var handlers = 0

    override fun parse(data: JsonObject) {

        val opcode = data.get("opcode").asInt
        val size = data.get("size").asInt

        if (data.has("handler")) {

            val handler = Class.forName(data.get("handler").asString).newInstance()

            if (handler is ReadablePacket) {
                PacketRepository.readers[opcode] = handler
                handlers++
            }

        }

        PacketRepository.sizes[opcode] = size
    }

    override fun onComplete() {
        logger.info("Loaded: $count packet sizes.")
        logger.info("Loaded: $handlers packet handlers.")
    }

}