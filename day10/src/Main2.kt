import classes.Map
import java.io.File

fun main() {
    val inputLines = File("day10/resources/input.txt").readLines()

    val map = Map(inputLines.map { it.toCharArray() }.toTypedArray())
    println(map.numberOfEnclosedTile)
}
