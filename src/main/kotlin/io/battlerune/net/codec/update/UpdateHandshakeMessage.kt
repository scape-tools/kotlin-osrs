package io.battlerune.net.codec.update

import io.battlerune.net.codec.handshake.HandshakeMessage

class UpdateHandshakeMessage(override val type: Int, override val response: Int, val version: Int) : HandshakeMessage