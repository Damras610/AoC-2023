import classes.Game
import java.io.File

fun main() {
    val inputLines = File("day2/resources/input.txt").readLines()
    val games = inputLines.map {
        Game.parse(it)
    }

    val result = games.sumOf {
        val minNumberOfRedCubes = it.rounds.maxOf { it.red }
        val minNumberOfGreenCubes = it.rounds.maxOf { it.green }
        val minNumberOfBlueCubes = it.rounds.maxOf { it.blue }
        minNumberOfRedCubes * minNumberOfGreenCubes * minNumberOfBlueCubes
    }
    println(result)
}
