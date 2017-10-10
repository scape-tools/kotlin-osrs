package io.battlerune.net.codec.login

import io.battlerune.net.crypt.ISAACCipherPair
import io.netty.channel.Channel

class LoginRequest(val username: String, val password: String, val resizeable: Boolean, val lowMem: Boolean, val isaacPair: ISAACCipherPair, val channel: Channel)