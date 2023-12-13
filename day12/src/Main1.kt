import classes.Spring
import java.io.File

fun main() {
    val inputLines = File("day12/resources/input.txt").readLines()

    val numberOfWorkingCombinaison = inputLines.map {
        Spring.parse(it)
    }.sumOf {
        it.numberOfSubstitutions()
    }
    println(numberOfWorkingCombinaison)
}
