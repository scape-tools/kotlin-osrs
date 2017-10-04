package io.battlerune.core.net.codec.js5

import io.battlerune.core.net.codec.handshake.HandshakeMessage

class JS5HandshakeMessage(override val type: Int, override val response: Int, val version: Int) : HandshakeMessage {}