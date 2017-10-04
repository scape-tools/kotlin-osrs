package io.battlerune.core.net.login

import io.battlerune.core.net.codec.handshake.HandshakeMessage

class LoginHandshakeMessage(override val type: Int, override val response: Int) : HandshakeMessage {}