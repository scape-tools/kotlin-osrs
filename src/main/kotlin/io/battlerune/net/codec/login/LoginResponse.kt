package io.battlerune.net.codec.login

import io.battlerune.net.crypt.IsaacRandomPair

class LoginResponse(val username: String, val password: String, val resizeable: Boolean, val lowMem: Boolean, val isaacPair: IsaacRandomPair)