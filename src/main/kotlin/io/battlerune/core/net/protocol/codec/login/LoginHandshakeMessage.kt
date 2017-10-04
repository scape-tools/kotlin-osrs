package io.battlerune.core.net.protocol.codec.login

import io.battlerune.core.net.protocol.codec.handshake.HandshakeMessage

class LoginHandshakeMessage(override val type: Int, override val response: Int) : HandshakeMessage {}