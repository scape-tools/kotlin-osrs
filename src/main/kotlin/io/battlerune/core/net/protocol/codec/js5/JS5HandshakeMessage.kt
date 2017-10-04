package io.battlerune.core.net.protocol.codec.js5

import io.battlerune.core.net.protocol.codec.handshake.HandshakeMessage

class JS5HandshakeMessage(override val type: Int, override val response: Int, val version: Int) : HandshakeMessage {}