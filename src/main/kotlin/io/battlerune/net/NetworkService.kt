package io.battlerune.net

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import net.openrs.cache.Cache
import net.openrs.cache.FileStore
import org.apache.logging.log4j.LogManager

class NetworkService {

    companion object {
        val logger = LogManager.getLogger()
        val cache = Cache(FileStore.open("./data/cache/"))
        val checksumTable = cache.createChecksumTable().encode()
    }

    fun start(port: Int) {
        val bossGroup = NioEventLoopGroup(1)
        val loopGroup = NioEventLoopGroup()
        try {
            val sb = ServerBootstrap()

            sb.group(bossGroup, loopGroup)
                    .channel(NioServerSocketChannel().javaClass)
                    .handler(LoggingHandler(LogLevel.INFO))
                    .childHandler(ServerPipelineInitializer())

            val f = sb.bind(port).syncUninterruptibly()

            logger.info("BattleRune bound to port $port.")

            f.channel().closeFuture().sync()

        } catch (ex : Exception) {
            ex.printStackTrace()
        } finally {
            bossGroup.shutdownGracefully()
            loopGroup.shutdownGracefully()
        }

    }

}