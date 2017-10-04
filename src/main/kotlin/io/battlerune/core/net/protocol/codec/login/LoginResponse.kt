package io.battlerune.core.net.protocol.codec.login

import io.battlerune.core.net.protocol.crypt.IsaacRandomPair

class LoginResponse(val username: String, val password: String, val resizeable: Boolean, val lowMem: Boolean, val isaacPair: IsaacRandomPair) {
}