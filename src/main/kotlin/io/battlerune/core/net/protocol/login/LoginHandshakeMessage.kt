package io.battlerune.core.net.protocol.login

import io.battlerune.core.net.protocol.handshake.HandshakeMessage

class LoginHandshakeMessage(override val type: Int, override val response: Int) : HandshakeMessage {}