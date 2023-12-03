import java.io.File

fun main() {
    val inputLines = File("day3/resources/input.txt").readLines()

    var sum = 0
    for (indexY in inputLines.indices) {
        for (indexX in 0 until inputLines[indexY].length) {
            if (indexX >= inputLines[indexY].length)
                break
            // Every time, we read a '*', calculate its gear ration and add it up.
            if (inputLines[indexY][indexX] == '*') {
                val gearRatio = getGearRatio(inputLines, indexX, indexY)
                sum += gearRatio
            }
        }
    }
    println(sum)
}

/**
 * Calculate the gear ration of a number
 */
fun getGearRatio(inputLines: List<String>, indexX: Int, indexY: Int): Int {
    // Number of Part numbers around a gear, starting with 0, we will increment that number
    // everytime a part number is read.
    var numberOfPartNumbers = 0

    // The initial value of every part number are 1 because, in the end, we multiply them all together
    var topNumber = 1
    var topLeftNumber = 1
    var topRightNumber = 1
    var leftNumber = 1
    var rightNumber = 1
    var bottomNumber = 1
    var bottomLeftNumber = 1
    var bottomRightNumber = 1

    // Detect if a digit is just above the gear. If so, there can only be one part number above the gear
    // E.g
    //  ...56.. or ..756.. or ...5...
    //  ...*...    ...*...    ...*...
    //  .......    .......    .......
    // Get the top number
    if (inputLines[indexY - 1][indexX].isDigit()) {
        topNumber = getNumber(inputLines[indexY - 1], indexX)
        numberOfPartNumbers += 1
    }
    // Else there can be one or two part number.
    // E.g
    //  .58.68. or .58.... or ....69.
    //  ...*...    ...*...    ...*...
    //  .......    .......    .......
    else {
        // Get the top left number
        if (inputLines[indexY - 1][indexX - 1].isDigit()) {
            topLeftNumber = getNumber(inputLines[indexY - 1], indexX - 1)
            numberOfPartNumbers += 1
        }
        // Get the top right number
        if (inputLines[indexY - 1][indexX + 1].isDigit()) {
            topRightNumber = getNumber(inputLines[indexY - 1], indexX + 1)
            numberOfPartNumbers += 1
        }
    }
    // Get the left number
    if (inputLines[indexY][indexX - 1].isDigit()) {
        leftNumber = getNumber(inputLines[indexY], indexX - 1)
        numberOfPartNumbers += 1
    }
    // Get the right number
    if (inputLines[indexY][indexX + 1].isDigit()) {
        rightNumber = getNumber(inputLines[indexY], indexX + 1)
        numberOfPartNumbers += 1
    }
    // Detect if a digit is just above the gear. If so, there can only be one part number above the gear
    // E.g
    //  ....... or ....... or .......
    //  ...*...    ...*...    ...*...
    //  ..756..    ...56..    ...5...
    // Get the bottom number
    if (inputLines[indexY + 1][indexX].isDigit()) {
        bottomNumber = getNumber(inputLines[indexY + 1], indexX)
        numberOfPartNumbers += 1
    } else {
        // Get the bottom left number
        if (inputLines[indexY + 1][indexX - 1].isDigit()) {
            bottomLeftNumber = getNumber(inputLines[indexY + 1], indexX - 1)
            numberOfPartNumbers += 1
        }
        // Get the bottom right number
        if (inputLines[indexY + 1][indexX + 1].isDigit()) {
            bottomRightNumber = getNumber(inputLines[indexY + 1], indexX + 1)
            numberOfPartNumbers += 1
        }
    }

    // If there are less than two part numbers, return 0 as the '*' is not considered as a gear
    if (numberOfPartNumbers < 2) {
        return 0
    }
    // Else, return the multiplication of all part numbers.
    return topNumber * topLeftNumber * topRightNumber * leftNumber * rightNumber * bottomNumber * bottomLeftNumber * bottomRightNumber
}

/**
 * The function to get a number from a designated digit
 */
fun getNumber(line: String, indexX: Int): Int {
    // Out of bounds protection
    if (indexX < 0 || indexX >= line.length || !line[indexX].isDigit())
        return 1

    // Save the designated digit
    var number: String = line[indexX].toString()

    // Read all digits BEFORE the designated digits and add them in a string before the designated digits
    var i = -1
    while (indexX + i >= 0 && line[indexX + i].isDigit()) {
        number = line[indexX + i].plus(number)
        i -= 1
    }

    // Read all digits AFTER the designated digits and add them in a string after the designated digits
    i = 1
    while (indexX + i < line.length && line[indexX + i].isDigit()) {
        number = number.plus(line[indexX + i])
        i += 1
    }

    // Return the number as an integer
    return number.toInt()
}