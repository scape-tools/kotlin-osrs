package io.battlerune.core.net.protocol.js5

import io.battlerune.core.net.protocol.handshake.HandshakeMessage

class JS5HandshakeMessage(override val type: Int, override val response: Int, val version: Int) : HandshakeMessage {}