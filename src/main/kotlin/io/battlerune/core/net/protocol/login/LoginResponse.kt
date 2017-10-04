package io.battlerune.core.net.protocol.login

import io.battlerune.util.IsaacRandomPair

class LoginResponse(val username: String, val password: String, val resizeable: Boolean, val lowMem: Boolean, val isaacPair: IsaacRandomPair) {
}