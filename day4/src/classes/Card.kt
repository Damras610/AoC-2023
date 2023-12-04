package classes

import kotlin.math.pow

data class Card(
    val id: Int,
    val winningNumbers: Set<Int>,
    val playedNumbers: Set<Int>
) {
    companion object {
        fun parse(inputString: String): Card {
            val id = inputString
                .substringAfter("Card")
                .substringBefore(":")
                .trim()
                .toInt()
            val winningNumbers = inputString
                .substringAfter(":")
                .substringBefore("|")
                .trim()
                .split("\\s+".toRegex())
                .map {
                    it.toInt()
                }.toSet()
            val playedNumbers = inputString
                .substringAfter("|")
                .substringBefore("\n")
                .trim()
                .split("\\s+".toRegex())
                .map {
                    it.toInt()
                }.toSet()

            return Card(
                id = id,
                winningNumbers = winningNumbers,
                playedNumbers = playedNumbers
            )
        }
    }

    fun calculatePoints(): Int {
        val numberOfGoodNumbers = goodNumbers.size
        return if (numberOfGoodNumbers == 0)
            0
        else
            2.0.pow((numberOfGoodNumbers - 1).toDouble()).toInt()
    }

    val goodNumbers = winningNumbers.intersect(playedNumbers)
}