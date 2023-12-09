import classes.ValueHistory
import java.io.File

fun main() {
    val inputLines = File("day9/resources/input.txt").readLines()

    val valueHistories = inputLines.map {
        ValueHistory.parse(it)
    }.sumOf {
        it.extrapolatePrevious()
    }

    println(valueHistories)

}