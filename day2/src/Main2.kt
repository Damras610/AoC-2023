import classes.Game
import classes.Round
import java.io.File

fun main() {
    val inputLines = File("day2/resources/input.txt").readLines()
    val games = inputLines.map {
        val gameId = it.substringAfter("Game ").substringBefore(":").toInt()
        val rounds = it.substringAfter(':').split(";")
            .map {
                var numberOfRedCubes = 0
                var numberOfBlueCubes = 0
                var numberOfGreenCubes = 0
                it.split(",").map {
                    it.trim()
                }.forEach {
                    if (it.endsWith("red"))
                        numberOfRedCubes = it.substringBefore(" ").toInt()
                    if (it.endsWith("blue"))
                        numberOfBlueCubes = it.substringBefore(" ").toInt()
                    if (it.endsWith("green"))
                        numberOfGreenCubes = it.substringBefore(" ").toInt()
                }
                Round(
                    red = numberOfRedCubes,
                    green = numberOfGreenCubes,
                    blue = numberOfBlueCubes
                )
            }
        Game(gameId, rounds)
    }

    val result = games.sumOf {
        val minNumberOfRedCubes = it.rounds.maxOf { it.red }
        val minNumberOfGreenCubes = it.rounds.maxOf { it.green }
        val minNumberOfBlueCubes = it.rounds.maxOf { it.blue }
        minNumberOfRedCubes * minNumberOfGreenCubes * minNumberOfBlueCubes
    }
    println(result)
}
