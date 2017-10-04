package io.battlerune.core.net.login

import io.netty.buffer.ByteBuf
import sun.plugin.dom.exception.InvalidStateException

enum class AuthorizationType {

    AUTHENTICATOR,

    NORMAL,

    TRUSTED_COMPUTER,

    TRUSTED_AUTHENTICATOR;

    companion object {

        fun lookup(id: Int) : AuthorizationType {
            if (id < 0 || id >= AuthorizationType.values().size) {
                throw InvalidStateException("id=$id must be >= 0 and < ${AuthorizationType.values().size}")
            }
            return AuthorizationType.values()[id]
        }

    }

    fun read(buf: ByteBuf) {
        when(this) {
            NORMAL -> {
                buf.readerIndex(buf.readerIndex() + 8)
            }

            TRUSTED_COMPUTER -> {
                buf.readInt()
                buf.readerIndex(buf.readerIndex() + 4)
            }

            AUTHENTICATOR, TRUSTED_AUTHENTICATOR -> {
                buf.readMedium()
                buf.readerIndex(buf.readerIndex() + 5)
            }
        }
    }

}