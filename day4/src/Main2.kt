import classes.Card
import classes.Pile
import java.io.File

fun main() {
    val inputLines = File("day4/resources/input.txt").readLines()

    val cards = inputLines.map {
        Card.parse(it)
    }
    val pile = Pile(cards.toSet())
    pile.process()
    println(pile.pileSize)
}