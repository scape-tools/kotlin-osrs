package io.battlerune.net.codec.handshake

class HandshakeMessage(val value : Int) {

    companion object {
        val VERSION_CURRENT = 0
        val VERSION_EXPIRED = 6
    }

}