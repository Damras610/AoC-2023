package classes

import java.lang.Exception

data class Mirror(
    val image: List<String>,
    val hasSmudge: Boolean = false
) {

    private val numberOfRows: Int = image.size
    private val numberOfCols: Int = image.first().length

    // Variable used by the function `findLineOfReflexion` to hold whether a smudge has been detected (and fixed).
    // False means that the smudge has not been detected. Otherwise, it is true.
    private var smudgeDetected: Boolean = false

    /**
     * It finds and return the row before which the line of reflexion is found. It also returns whether the line is
     * horizontal of vertical
     */
    fun findLineOfReflexion(): Pair<Boolean, Int> {
        val vLine = findVLineOfReflexion()
        val hLine = findHLineOfReflexion()
        if (vLine != null) return vLine
        if (hLine != null) return hLine
        throw Exception("No reflection in mirror $this")
    }

    /**
     * Find the vertical line of reflexion
     */
    private fun findVLineOfReflexion(): Pair<Boolean, Int>? {
        loop@ for (i in 0 until numberOfCols - 1) {
            smudgeDetected = false
            var leftColIdx = i
            var rightColIdx = i + 1
            while (leftColIdx >= 0 && rightColIdx <= numberOfCols - 1) {
                if (!checkSameCol(leftColIdx, rightColIdx))
                    continue@loop
                leftColIdx -= 1
                rightColIdx += 1
            }
            // If the line of reflexion is found return it.
            // If the mirror has a smudge, then we must have found and fixed it
            if (!hasSmudge || smudgeDetected)
                return Pair(true, i+1)
        }
        return null
    }

    /**
     * Check whether two columns are identical. If `hasSmudge` is activated, one difference is tolerated
     */
    private fun checkSameCol(left: Int, right: Int): Boolean {
        for (i in 0 until numberOfRows) {
            if (image[i][left] != image[i][right]) {
                if (!hasSmudge || smudgeDetected)
                    return false
                else smudgeDetected = true
            }
        }
        return true
    }

    /**
     * Find the horizontal line of reflexion
     */
    private fun findHLineOfReflexion(): Pair<Boolean, Int>? {
        loop@ for (i in 0 until numberOfRows - 1) {
            smudgeDetected = false
            var topRowIdx = i
            var bottomRowIdx = i + 1
            while (topRowIdx >= 0 && bottomRowIdx <= numberOfRows - 1) {
                if (!checkSameRow(topRowIdx, bottomRowIdx))
                    continue@loop
                topRowIdx -= 1
                bottomRowIdx += 1
            }
            // If the line of reflexion is found return it.
            // If the mirror has a smudge, then we must have found and fixed it
            if (!hasSmudge || smudgeDetected)
                return Pair(false, i+1)
        }
        return null
    }

    /**
     * Check whether two rows are identical. If `hasSmudge` is activated, one difference is tolerated
     */
    private fun checkSameRow(top: Int, bottom: Int): Boolean {
        for (i in 0 until numberOfCols) {
            if (image[top][i] != image[bottom][i]) {
                if (!hasSmudge || smudgeDetected)
                    return false
                else smudgeDetected = true
            }
        }
        return true
    }
}