import java.io.File
import java.lang.Exception

fun main() {
    val inputLines = File("day1/resources/input.txt").readLines()
    val sum = inputLines.map {
        getFirstDigit(it) * 10 + getLastDigit(it)
    }.sum()
    println(sum)
}

fun getFirstDigit(line: String) : Int {
    for (idx in 0..line.length) {
        if (line[idx].isDigit()) {
            return line[idx].digitToInt()
        }
        else {
            line.subSequence(idx, line.length).let {
                when {
                    it.startsWith("one") -> return 1
                    it.startsWith("two") -> return 2
                    it.startsWith("three") -> return 3
                    it.startsWith("four") -> return 4
                    it.startsWith("five") -> return 5
                    it.startsWith("six") -> return 6
                    it.startsWith("seven") -> return 7
                    it.startsWith("eight") -> return 8
                    it.startsWith("nine") -> return 9
                    else -> {}
                }
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
                when {
                    it.startsWith("one") -> return 1
                    it.startsWith("two") -> return 2
                    it.startsWith("three") -> return 3
                    it.startsWith("four") -> return 4
                    it.startsWith("five") -> return 5
                    it.startsWith("six") -> return 6
                    it.startsWith("seven") -> return 7
                    it.startsWith("eight") -> return 8
                    it.startsWith("nine") -> return 9
                    else -> {}
                }
            }
        }
    }
    throw Exception("Line is $line")
}