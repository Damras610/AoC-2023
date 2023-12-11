import classes.Space
import java.io.File
import kotlin.math.abs

fun main() {
    val inputLines = File("day11/resources/input.txt").readLines()

    val space = Space(inputLines)

    val galaxies = space.galaxies(Space.SMALL_SPACE_MULTIPLIER)
    val galaxiesCopy = galaxies.toMutableList()

    // Sum of the distance between the galaxies two by two
    val sumOfDistance = galaxies.sumOf { a ->
        galaxiesCopy.removeAt(0)
        galaxiesCopy.sumOf { b ->
            abs(a.first - b.first) + abs(a.second - b.second)
        }
    }

    println(sumOfDistance)
}
