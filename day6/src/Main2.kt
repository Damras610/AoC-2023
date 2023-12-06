import java.io.File

fun main() {
    val inputLines = File("day6/resources/input.txt").readLines()

    val raceTime = inputLines[0].substringAfter(":").replace(" ", "").toInt()
    val raceRecord = inputLines[1].substringAfter(":").replace(" ", "").toLong()

    val numberOfWays = calculatedNumberOfWaysToWin(raceTime, raceRecord)

    println(numberOfWays)
}

fun calculatedNumberOfWaysToWin(time: Int, record: Long): Int {
    return IntRange(0, time).count { timeHoldingButton ->
        (time.toLong() - timeHoldingButton) * timeHoldingButton > record
    }
}