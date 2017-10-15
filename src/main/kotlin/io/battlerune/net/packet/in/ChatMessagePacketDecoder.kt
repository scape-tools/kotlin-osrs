package io.battlerune.net.packet.`in`

import io.battlerune.game.event.impl.ChatMessageEvent
import io.battlerune.game.world.actor.pawn.player.Player
import io.battlerune.net.codec.game.RSByteBufReader
import io.battlerune.net.packet.PacketDecoder

class ChatMessagePacketDecoder : PacketDecoder<ChatMessageEvent> {

    override fun decode(player: Player, reader: RSByteBufReader): ChatMessageEvent? {
        reader.readByte() // ?
        val color = reader.readByte()
        val effect = reader.readByte()
        val compressedLength = reader.readUSmart() // length
        val compressed = reader.readBytes(reader.size())
        val decompressed = ByteArray(512)
        val huffman = player.context.huffman
        huffman.decompress(compressed, decompressed, compressedLength)
        val msg = String(decompressed)
        return ChatMessageEvent(player, msg, color, effect)
    }

}