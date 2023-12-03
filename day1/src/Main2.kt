import java.io.File
import java.lang.Exception

fun main() {
    val inputLines = File("day1/resources/input.txt").readLines()
    val sum = inputLines.sumOf {
        getFirstDigit(it) * 10 + getLastDigit(it)
    }
    println(sum)
}

fun getFirstDigit(line: String) : Int {
    for (idx in 0..line.length) {
        if (line[idx].isDigit()) {
            return line[idx].digitToInt()
        }
        else {
            line.subSequence(idx, line.length).let {
                val digit = getDigitFromName(it)
                if (digit != 0) return digit
            }
        }
    }
    throw Exception("Line is $line")
}

fun getLastDigit(line: String): Int {
    for (idx in 0..line.length) {
        if (line[line.length - 1 - idx].isDigit()) {
            return line[line.length - 1 - idx].digitToInt()
        }
        else {
            line.subSequence(line.length - 1 - idx, line.length).let {
                val digit = getDigitFromName(it)
                if (digit != 0) return digit
            }
        }
    }
    throw Exception("Line is $line")
}

fun getDigitFromName(name: CharSequence): Int {
    return when {
        name.startsWith("one") -> 1
        name.startsWith("two") -> 2
        name.startsWith("three") -> 3
        name.startsWith("four") -> 4
        name.startsWith("five") -> 5
        name.startsWith("six") -> 6
        name.startsWith("seven") -> 7
        name.startsWith("eight") -> 8
        name.startsWith("nine") -> 9
        else -> 0
    }
}