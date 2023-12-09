package classes

data class ValueHistory(
    val history: List<Int>
) {
    companion object {
        fun parse(inputLine: String): ValueHistory {
            return ValueHistory(inputLine.split(" ").map { it.toInt() })
        }
    }

    /**
     * Extrapolate the next number after the last measured
     */
    fun extrapolateNext(): Int {
        val differentialSequences = getDifferentialSequences(history).asReversed().toMutableList()
        for (i in 0 until differentialSequences.size) {
            if (i == 0) {
                differentialSequences[0] = differentialSequences[0].plus(0)
            } else {
                differentialSequences[i] = differentialSequences[i].plus(differentialSequences[i].last() + differentialSequences[i-1].last())
            }
        }
        return history.last() + differentialSequences[differentialSequences.size - 1].last()
    }

    /**
     * Extrapolate the previous number before the first measured
     */
    fun extrapolatePrevious(): Int {
        val differentialSequences = getDifferentialSequences(history).asReversed().toMutableList()
        for (i in 0 until differentialSequences.size) {
            if (i == 0) {
                differentialSequences[0] = listOf(0).plus(differentialSequences[0])
            } else {
                differentialSequences[i] = listOf(differentialSequences[i].first() - differentialSequences[i-1].first()).plus(differentialSequences[i])
            }
        }
        return history.first() - differentialSequences[differentialSequences.size - 1].first()
    }

    /**
     * Get the differential subsequences from a sequence.
     */
    private fun getDifferentialSequences(currentSequence: List<Int>): List<List<Int>> {
        val differentialSequence = currentSequence.zipWithNext { a, b -> b - a }
        return if (differentialSequence.all { it == 0 })
            listOf(differentialSequence)
        else
            listOf(differentialSequence).plus(getDifferentialSequences(differentialSequence))
    }
}