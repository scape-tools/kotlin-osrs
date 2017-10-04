package io.battlerune.net.codec.login

import io.battlerune.util.ByteBufUtil
import io.battlerune.net.crypt.IsaacRandom
import io.battlerune.net.crypt.IsaacRandomPair
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import java.math.BigInteger



class LoginDecoder : ByteToMessageDecoder() {

    companion object {
        val MODULUS = BigInteger(
                "94904992129904410061849432720048295856082621425118273522925386720620318960919649616773860564226013741030211135158797393273808089000770687087538386210551037271884505217469135237269866084874090369313013016228010726263597258760029391951907049483204438424117908438852851618778702170822555894057960542749301583313")

        val EXPONENT = BigInteger(
                "72640252303588278644467876834506654511692882736878142674473705672822320822095174696379303197013981434572187481298130748148385818094460521624198552406940508805602215708418094058951352076283100448576575511642453669107583920561043364042814766866691981132717812444681081534760715694225059124574441435942822149161")

    }

    override fun decode(ctx: ChannelHandlerContext, inc: ByteBuf, out: MutableList<Any>) {

        if (!inc.isReadable) {
            return
        }

        val bytes = ByteArray(inc.readShort().toInt())
        inc.readBytes(bytes)

        val rsaBuf = Unpooled.wrappedBuffer(BigInteger(bytes).modPow(EXPONENT, MODULUS).toByteArray())

        val opcode = rsaBuf.readByte()

        if (opcode.toInt() != 1) {
            return
        }

        val authType = AuthorizationType.lookup(rsaBuf.readByte().toInt())

        val clientKeys = IntArray(4)

        for (i in 0 until clientKeys.size) {
            clientKeys[i] = rsaBuf.readInt()
        }

        authType.read(rsaBuf)

        val password = io.battlerune.util.ByteBufUtil.readString(rsaBuf)

        val xteaBuf = io.battlerune.util.ByteBufUtil.decryptXTEA(inc, clientKeys)

        val username = io.battlerune.util.ByteBufUtil.readString(xteaBuf)

        val resizableAndMemory = xteaBuf.readByte()

        val resizable = (resizableAndMemory.toInt() shr 1) == 1

        val lowMem = (resizableAndMemory.toInt() and 1) == 1

        val width = xteaBuf.readShort()

        val height = xteaBuf.readShort()

        // some bytes for cache
        xteaBuf.skipBytes(24)

        val token = io.battlerune.util.ByteBufUtil.readString(xteaBuf)

        xteaBuf.readInt()

        // machine info
        xteaBuf.readByte() // machine info opcode 6
        xteaBuf.readByte() // os type
        xteaBuf.readByte() // 64 bit
        xteaBuf.readByte() // os version
        xteaBuf.readByte() // vendor
        xteaBuf.readByte() // major
        xteaBuf.readByte() // minor
        xteaBuf.readByte() // patch
        xteaBuf.readByte() // some flag
        xteaBuf.readShort() // max memory
        xteaBuf.readByte()
        xteaBuf.readMedium()
        xteaBuf.readShort()
        ByteBufUtil.readJagString(xteaBuf)
        ByteBufUtil.readJagString(xteaBuf)
        ByteBufUtil.readJagString(xteaBuf)
        ByteBufUtil.readJagString(xteaBuf)
        xteaBuf.readByte()
        xteaBuf.readShort()
        ByteBufUtil.readJagString(xteaBuf)
        ByteBufUtil.readJagString(xteaBuf)
        xteaBuf.readByte()
        xteaBuf.readByte()

        xteaBuf.readInt()
        xteaBuf.readInt()
        xteaBuf.readInt()
        xteaBuf.readInt()

        // end of machine info

        xteaBuf.readByte()
        xteaBuf.readInt() // crc opcode 0

        val crc = IntArray(17)

        for (i in 0 until crc.size) {
            crc[i] = xteaBuf.readInt()
        }

        val serverKeys = IntArray(4)

        for (i in 0 until serverKeys.size) {
            serverKeys[i] = clientKeys[i] + 50
        }

        val isaacPair = IsaacRandomPair(IsaacRandom(serverKeys), IsaacRandom(clientKeys))

        out.add(LoginResponse(username, password, resizable, lowMem, isaacPair))

    }

}