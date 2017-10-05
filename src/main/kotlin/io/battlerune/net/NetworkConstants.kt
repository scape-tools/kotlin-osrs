package io.battlerune.net

import io.netty.util.AttributeKey
import io.battlerune.net.channel.PlayerChannel

object NetworkConstants {

    val PORT = 43594

    val SESSION_KEY = AttributeKey.valueOf<PlayerChannel>("session.key")

    val PACKET_LIMIT = 30

}