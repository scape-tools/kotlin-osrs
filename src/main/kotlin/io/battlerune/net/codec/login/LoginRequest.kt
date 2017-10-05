package io.battlerune.net.codec.login

import io.battlerune.game.GameContext
import io.battlerune.net.crypt.IsaacRandomPair
import io.netty.channel.Channel

class LoginRequest(val username: String, val password: String, val resizeable: Boolean, val lowMem: Boolean, val isaacPair: IsaacRandomPair, val gameContext: GameContext, val channel: Channel)