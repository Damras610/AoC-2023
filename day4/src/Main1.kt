import classes.Card
import java.io.File

fun main() {
    val inputLines = File("day4/resources/input.txt").readLines()

    val totalPoints = inputLines.map {
        Card.parse(it)
    }.sumOf {
        it.calculatePoints()
    }
    println(totalPoints)
}