package io.battlerune.protocol

class ConnectionType(val opcode: Int) {
    companion object {
        val GAME_SERVER_OPCODE = 14
        val FILE_SERVER_OPCODE = 15
    }
}