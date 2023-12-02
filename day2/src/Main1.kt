import classes.Game
import java.io.File

fun main() {
    val inputLines = File("day2/resources/input.txt").readLines()
    val games = inputLines.map {
        Game.parse(it)
    }

    val result = games.filter {
        it.rounds.none { it.red > 12 || it.green > 13 || it.blue > 14 }
    }.sumOf { it.id }
    println(result)
}
