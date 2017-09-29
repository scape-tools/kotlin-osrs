package io.battlerune.core

import io.battlerune.net.ServerPipelineInitializer
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler

class Server {

    fun bind(port: Int) {
        val bossGroup = NioEventLoopGroup(1)
        val loopGroup = NioEventLoopGroup()
        try {
            val sb = ServerBootstrap()

            sb.group(bossGroup, loopGroup)
                    .channel(NioServerSocketChannel().javaClass)
                    .handler(LoggingHandler(LogLevel.INFO))
                    .childHandler(ServerPipelineInitializer())

            val f = sb.bind(port).syncUninterruptibly()

            println("BattleRune bound to port $port")

            f.channel().closeFuture().sync()

        } catch (ex : Exception) {
            ex.printStackTrace()
        } finally {
            bossGroup.shutdownGracefully()
            loopGroup.shutdownGracefully()
        }

    }

}