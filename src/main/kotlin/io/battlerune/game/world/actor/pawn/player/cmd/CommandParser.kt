package io.battlerune.game.world.actor.pawn.player.cmd

class CommandParser(val input: String) {

    val args: List<String> = if(input.indexOf(" ") != -1) input.trim().split(" ") else throw IllegalStateException("Command $input has no arguments.")

    val cmd: String = args[0]

    var pointer = 0

    fun nextString() : String {
        if (pointer >= args.size) {
            throw ArrayIndexOutOfBoundsException("The next argument does not exist. [Size: ${args.size}, Attempted: $pointer]")
        }
        return args[++pointer]
    }

    fun nextLine() : String {
        val builder = StringBuilder()
        while(hasNext()) {
            val string = nextString()

            if (string.contains("\\n")) {
                break
            }

            builder.append(" ").append(string)
        }
        return builder.toString()
    }

    fun nextInt() : Int {
        return nextString().toInt()
    }

    fun nextLong() : Long {
        return nextString().toLong()
    }

    fun hasNext(length: Int = 1): Boolean {
        return pointer + length < args.size
    }

}