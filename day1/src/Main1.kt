import java.io.File

fun main() {
    val inputLines = File("day1/resources/input.txt").readLines()
    val sum = inputLines.sumOf {
        val firstDigit = it.first { it.isDigit() }
        val lastDigit = it.last { it.isDigit() }
        "$firstDigit$lastDigit".toInt()
    }
    println(sum)
}
