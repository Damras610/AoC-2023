import java.io.File

fun main() {
    val inputLines = File("day6/resources/input.txt").readLines()

    val races = inputLines[0].substringAfter(":").trim().split("\\s+".toRegex()).map { it.toInt() }
        .zip(inputLines[1].substringAfter(":").trim().split("\\s+".toRegex()).map { it.toInt() })

    val numberOfWays = races.map {
        calculatedNumberOfWaysToWin(it.first, it.second)
    }.reduce { acc, numberOfWays -> acc * numberOfWays }

    println(numberOfWays)
}

fun calculatedNumberOfWaysToWin(time: Int, record: Int): Int {
    return IntRange(0, time).count { timeHoldingButton ->
        (time - timeHoldingButton) * timeHoldingButton > record
    }
}