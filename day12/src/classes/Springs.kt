package classes

import kotlin.text.StringBuilder

data class Spring(
    val springStates: List<String>,
    val groups: List<Int>
) {
    companion object {
        fun parse(inputLine: String, foldedUp: Boolean = false): Spring {
            // Get the sprint states
            val springStates = inputLine.substringBefore(" ").let {
                // If the input is folded up, unfold it
                if (foldedUp)
                    it.plus("?").repeat(5).removeSuffix("?")
                // else, return the read input
                else it
            }.split(Regex("\\.+")).filter { it.isNotEmpty() }
            // Get the groups
            val groups = inputLine.substringAfter(" ").let {
                // If the input is folded up, unfold it
                if (foldedUp)
                    it.plus(",").repeat(5).removeSuffix(",")
                // else, return the read input
                else
                    it
            }.split(",").map { it.toInt() }
            return Spring(springStates, groups)
        }
    }

    val simplifiedSpringState: List<String>
    val simplifiedGroups: List<Int>
    init {
        val (_simplifiedSpringState, _simplifiedGroups) = simplifySprings()
        simplifiedSpringState = _simplifiedSpringState
        simplifiedGroups = _simplifiedGroups
    }

    private fun simplifySprings(): Pair<List<String>, List<Int>> {
        val simpSpringStates = springStates.toMutableList()
        val simpGroups = groups.toMutableList()
        // While the first spring state group does not contain any '?' characters, we can remove it as it is not
        // changeable anyhow. Then, we can also remove the first entry in the groups list
        while (!simpSpringStates.first().contains('?')) {
            simpSpringStates.removeAt(0)
            simpGroups.removeAt(0)
        }
        // Same for the last entries
        while (!simpSpringStates.last().contains('?')) {
            simpSpringStates.removeAt(simpSpringStates.size - 1)
            simpGroups.removeAt(simpGroups.size - 1)
        }


        return Pair(simpSpringStates, simpGroups)
    }

    fun numberOfSubstitutions(): Int {
        val springStatesTries = simplifiedSpringState.map {
            possibleSubstitutions(it)
        }

        return numberOfSubstitutionsLoop(springStatesTries, simplifiedGroups)
    }

    private fun numberOfSubstitutionsLoop(springStatesTries: List<List<List<Int>>>, groups: List<Int>): Int {
        if (springStatesTries.isEmpty() && groups.isEmpty()) {
            return 1
        }

        if (springStatesTries.isEmpty()) {
            return 0
        }


        var numberOfSubstitutions = 0
        for (j in 0 until springStatesTries[0].size) {
            if (groups.startsWith(springStatesTries[0][j])) {
                numberOfSubstitutions += numberOfSubstitutionsLoop(
                    springStatesTries.subList(
                        1,
                        springStatesTries.size
                    ), groups.subList(springStatesTries[0][j].size, groups.size)
                )
            }
        }
        return numberOfSubstitutions
    }

    fun possibleSubstitutions(springStateGroup: String): List<List<Int>> {
        val numberOfSpringGroups = simplifiedGroups.size
        val numberOfSprings = simplifiedGroups.sumOf { it }
        val largestSpringGroup = simplifiedGroups.max()

        val substitutions = mutableListOf<List<Int>>()
        val numberOfCombinaison = Math.pow(2.0, springStateGroup.count { it == '?' }.toDouble()).toInt()
        for (i in 0 until numberOfCombinaison) {
            var questionMarkIndex = 0
            val stringBuilder = StringBuilder("")
            springStateGroup.forEach { c ->
                if (c == '?') {
                    if ((i shr questionMarkIndex) % 2 == 0)
                        stringBuilder.append("#")
                    else
                        stringBuilder.append(".")
                    questionMarkIndex += 1
                } else {
                    stringBuilder.append('#')
                }
            }
            val substitution = stringBuilder.toString().split(Regex("\\.+")).filter { it.isNotEmpty() }.map { it.count() }
            if (substitution.size <= numberOfSpringGroups && (substitution.isEmpty() || (substitution.sumOf { it } <= numberOfSprings && substitution.max() <= largestSpringGroup)))
                substitutions.add(substitution)
        }
        return substitutions
    }
}

fun List<Int>.startsWith(start: List<Int>): Boolean {
    if (this.size < start.size)
        return false
    for (i in 0 until start.size) {
        if (this[i] != start[i])
            return false
    }
    return true
}