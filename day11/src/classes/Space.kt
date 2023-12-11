package classes

class Space(private val observation: List<String>) {

    companion object {
        const val SMALL_SPACE_MULTIPLIER = 2
        const val BIG_SPACE_MULTIPLIER = 1000000
    }

    /**
     * Return the list of empty rows and empty columns from the observation
     */
    private fun emptySpaces() : Pair<Set<Long>, Set<Long>> {
        val galaxyEmptyRows = LongRange(0, (observation.size - 1).toLong()).toMutableSet()
        val galaxyEmptyColumns = LongRange(0, (observation.first().length - 1).toLong()).toMutableSet()

        observation.forEachIndexed { observationRowIdx, observationRow ->
            val galaxiesIndex = observationRow.mapIndexedNotNull { index, char -> if (char == '#') index else null }
            if (galaxiesIndex.isNotEmpty()) {
                galaxyEmptyRows.remove(observationRowIdx.toLong())
                galaxiesIndex.forEach { observationColIdx -> galaxyEmptyColumns.remove(observationColIdx.toLong()) }
            }
        }


        return Pair(galaxyEmptyRows, galaxyEmptyColumns)
    }

    /**
     * Return the coordinates of all the galaxies.
     *
     * @param expansion the number of rows/columns by which the empty rows/columns should be replaced
     */
    fun galaxies(expansion: Int) : Set<Pair<Long, Long>> {
        val emptySpaces = emptySpaces()
        val galaxies = emptySet<Pair<Long, Long>>().toMutableSet()
        observation.forEachIndexed { rowIdx, row ->
            row.forEachIndexed { colIdx, char ->
                if (char == '#') {
                    val rowShift = emptySpaces.first.count { it < rowIdx } * (expansion - 1)
                    val colShift = emptySpaces.second.count { it < colIdx } * (expansion - 1)
                    galaxies.add(Pair((rowIdx + rowShift).toLong(), (colIdx + colShift).toLong()))
                }
            }
        }
        return galaxies
    }
}