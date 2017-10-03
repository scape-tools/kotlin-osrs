package io.battlerune.net.login

class LoginRequest(val type: Int, val version: Int){

    companion object {
        val NEW_CONNECTION = 16
        val RECONNECTING = 18
    }

}