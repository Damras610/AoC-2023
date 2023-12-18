import classes.Mirror
import java.io.File

fun main() {
    val inputLines = File("day13/resources/input.txt").readLines()

    val mirrors = mutableListOf<Mirror>()

    var buffer = mutableListOf<String>()
    for (line in inputLines) {
        if (line.isEmpty()) {
            mirrors.add(Mirror(buffer, true))
            buffer = mutableListOf()
        } else
            buffer.add(line)
    }
    mirrors.add(Mirror(buffer, true))

    val lineOfReflexions = mirrors.sumOf {
        val line = it.findLineOfReflexion()
        if (line.first) line.second
        else line.second * 100
    }

    println(lineOfReflexions)
}
