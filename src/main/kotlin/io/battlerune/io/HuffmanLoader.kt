package io.battlerune.io

import io.battlerune.game.GameContext
import io.battlerune.game.fs.Huffman
import io.battlerune.game.task.StartupTask

class HuffmanLoader(val context: GameContext) : StartupTask<HuffmanLoader>(HuffmanLoader::class.java) {

    override fun load(): Boolean {
        val fileId = context.cache.getFileId(10, "huffman")
        val buffer = context.cache.read(10, fileId).data
        val size = buffer.remaining()
        val data = ByteArray(size)
        buffer.get(data)
        context.huffman = Huffman(data)
        return true
    }

    override fun onComplete() {
        logger.info("Loaded huffman")
    }

}