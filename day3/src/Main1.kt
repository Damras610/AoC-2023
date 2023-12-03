import java.io.File
import kotlin.math.max
import kotlin.math.min

fun main() {
    val inputLines = File("day3/resources/input.txt").readLines()

    var sum = 0
    // For all lines
    for (indexY in inputLines.indices) {
        var shift = 0
        // For all characters
        for (indexX in 0 until inputLines[indexY].length) {
            // Index out of bound protection
            if (indexX + shift >= inputLines[indexY].length)
                break
            // If the character is a digit...
            if (inputLines[indexY][indexX + shift].isDigit()) {
                // ... get the attached number
                val number = getNumber(inputLines, indexX + shift, indexY)
                // ... and verify if it is a part number
                if (numberPartOfEngine(inputLines, indexX + shift, indexY, number)) {
                    // If so, add it up
                    sum += number.toInt()
                }
                // shift is used to ignore all the digits already considered in a part number.
                shift += number.length - 1
            }
        }
    }
    println(sum)
}

/**
 * Function to get the number attached to a digit.
 */
fun getNumber(inputLines: List<String>, x: Int, y: Int): String {
    var idx = 0
    var number = ""
    // Read all digits following the designated digit.
    // The loop is stopped when out of bounds or when the read character is not a digit.
    do {
        number = number.plus(inputLines[y][x + idx])
        idx += 1
    } while (x + idx < inputLines[y].length && inputLines[y][x + idx].isDigit())
    return number
}

/**
 * Verify if a number is a part number (e.g. if there is a symbol around it.
 */
fun numberPartOfEngine(inputLines: List<String>, x: Int, y: Int, number: String): Boolean {
    // Delimit the rectangle around a number
    val left = max(x - 1, 0)
    val right = min(x + number.length, inputLines[y].length - 1)
    val top = max(y - 1, 0)
    val bottom = min(y + 1, inputLines.size - 1)

    // For every character in that rectangle, if any one is a symbol return true.
    for (j in top .. bottom) {
        for (i in left .. right) {
            if (!inputLines[j][i].isLetterOrDigit() &&
                inputLines[j][i] != '.' &&
                inputLines[j][i] != '\n')
                return true
        }
    }
    // Else, return false
    return false
}