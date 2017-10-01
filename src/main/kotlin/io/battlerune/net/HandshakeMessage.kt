package io.battlerune.net

class HandshakeMessage(val value : Int) {

    companion object {
        val CONTINUE = 0
        val EXPIRED = 6
    }

}