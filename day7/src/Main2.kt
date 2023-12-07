import classes.Hand
import java.io.File

fun main() {
    val inputLines = File("day7/resources/input.txt").readLines()

    val winnings = inputLines.map {
        Hand.parse(it, jokerVariant = true)
    }.sorted().mapIndexed { index, hand ->
        println(hand)
        hand.bid.toLong() * (index + 1)
    }.sum()

    println(winnings)
}