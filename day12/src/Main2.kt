import classes.Spring
import classes.Spring2
import java.io.File

fun main() {
    val inputLines = File("day12/resources/test.txt").readLines()


    val numberOfWorkingCombinaison = inputLines.map {
        Spring2.parse(it, foldedUp = true)
    }.sumOf {
        println(it)
        val after = it.numberOfSubstitutions()
        println(after)
        after
    }
    println(numberOfWorkingCombinaison)
}