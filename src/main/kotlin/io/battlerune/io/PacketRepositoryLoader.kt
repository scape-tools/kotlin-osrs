package io.battlerune.io

import com.google.gson.JsonObject
import io.battlerune.game.event.Event
import io.battlerune.net.packet.PacketRepository
import io.battlerune.net.packet.PacketDecoder
import io.battlerune.util.GsonParser
import org.apache.logging.log4j.LogManager

class PacketRepositoryLoader : GsonParser("./data/packet_repository.json") {

    companion object {
        val logger = LogManager.getLogger()
    }

    var decoders = 0

    override fun parse(data: JsonObject) {

        val opcode = data.get("opcode").asInt
        val size = data.get("size").asInt

        if (data.has("decoder")) {

            val decoder = Class.forName(data.get("decoder").asString).newInstance()

            if (decoder is PacketDecoder<Event>) {
                PacketRepository.decoders[opcode] = decoder
                decoders++
            }

        }

        PacketRepository.sizes[opcode] = size
    }

    override fun onComplete() {
        logger.info("Loaded: $count packet sizes.")
        logger.info("Loaded: $decoders packet decoders.")
    }

}