import classes.Hand
import java.io.File

fun main() {
    val inputLines = File("day7/resources/input.txt").readLines()

    val winnings = inputLines.map {
        Hand.parse(it)
    }.sorted().mapIndexed { index, hand ->
        hand.bid.toLong() * (index + 1)
    }.sum()

    println(winnings)
}