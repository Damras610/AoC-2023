package classes

import kotlin.math.min

data class Spring2(
    val springStates: String,
    val groups: List<Int>
) {
    companion object {
        fun parse(inputLine: String, foldedUp: Boolean = false): Spring2 {
            // Get the sprint states
            val springStates = inputLine.substringBefore(" ").let {
                // If the input is folded up, unfold it
                if (foldedUp)
                    it.plus("?").repeat(5).removeSuffix("?")
                // else, return the read input
                else it
            }
            // Get the groups
            val groups = inputLine.substringAfter(" ").let {
                // If the input is folded up, unfold it
                if (foldedUp)
                    it.plus(",").repeat(5).removeSuffix(",")
                // else, return the read input
                else
                    it
            }.split(",").map { it.toInt() }
            return Spring2(springStates, groups)
        }
    }

    fun numberOfSubstitutions(): Int {
        return numberOfSubstitutionsLoop(springStates, groups)
    }

    fun numberOfSubstitutionsLoop(springStates: String, groups: List<Int>): Int {
        if (groups.isEmpty() && !springStates.contains("#")) return 1
        if (groups.isEmpty()) return 0

        val groupsSum = groups.sum()
        if (groupsSum > springStates.largestPossibleSize()) return 0
        if (groupsSum < springStates.smallestPossibleSize()) return 0

        val currentGroup = groups[0]
        var numberOfSubstitutions = 0
        var springStatesVar = springStates
        while (springStatesVar.isNotEmpty()) {
            if (groupsSum + groups.size - 2 > springStates.length) return 0
            when (springStatesVar[0]) {
                '.' -> {
                    springStatesVar = springStatesVar.removeRange(0, 1)
                }
                '#', '?' -> {
                    val smallestNextGroupSize = springStatesVar.smallestPossibleNextGroupSize()
                    val largestNextGroupSize = springStatesVar.largestPossibleNextGroupSize()
                    val nextGroupMandatory = springStatesVar.isNextGroupMandatory()

                    // Check if there is no more combination. If not, stop the loop and return the possible substitutions found
                    if (smallestNextGroupSize > currentGroup)
                        return 0
                    else if (largestNextGroupSize < currentGroup) {
                        if (nextGroupMandatory)
                            return 0
                        else
                            springStatesVar = springStatesVar.removeRange(0, largestNextGroupSize)
                    }

                    // If the group is only composed of dash, skip it.
                    else if (smallestNextGroupSize == currentGroup) {
                        val nextString =
                            if (springStatesVar.length > currentGroup)
                                springStatesVar.substring(currentGroup + 1)
                            else
                                springStatesVar.substring(currentGroup)
                        val nextGroups = groups.subList(1, groups.size)
                        numberOfSubstitutions += numberOfSubstitutionsLoop(nextString, nextGroups)

                        return numberOfSubstitutions
                    }

                    else {
                        if (nextGroupMandatory) {
                            val indexOfFirstDash = springStatesVar.indexOfFirst { it == '#' }

                            val springStateRangeToTest = springStatesVar.substring(0, min(indexOfFirstDash + currentGroup, largestNextGroupSize))
                            for (substitution in possibleSubstitutions(springStateRangeToTest)) {
                                var substitutionVar = substitution
                                while (substitutionVar.firstOrNull() == '.') { substitutionVar = substitutionVar.removePrefix(".") }
                                if (substitutionVar.smallestPossibleNextGroupSize() == currentGroup) {
                                    var nextString = springStatesVar.replaceFirst(springStateRangeToTest, substitutionVar)
                                    numberOfSubstitutions += numberOfSubstitutionsLoop(nextString, groups)
                                }
                            }
                            return numberOfSubstitutions
                        } else {
                            val springStateRangeToTest = springStatesVar.substring(0, largestNextGroupSize)
                            for (substitution in possibleSubstitutions(springStateRangeToTest)) {
                                var substitutionVar = substitution
                                while (substitutionVar.firstOrNull() == '.') { substitutionVar = substitutionVar.removePrefix(".") }
                                if (substitutionVar.smallestPossibleNextGroupSize() == currentGroup) {
                                    var nextString = springStatesVar.replaceFirst(springStateRangeToTest, substitutionVar)
                                    numberOfSubstitutions += numberOfSubstitutionsLoop(nextString, groups)
                                }
                            }
                            return numberOfSubstitutions
                        }
                    }
                }
            }
        }
        return numberOfSubstitutions
    }

    fun possibleSubstitutions(springStateGroup: String): List<String> {
        val substitutions = mutableListOf<String>()
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
            substitutions.add(stringBuilder.toString())
        }
        return substitutions
    }
}

fun String.largestPossibleSize(): Int {
    return count { it != '.' }
}

fun String.smallestPossibleSize(): Int {
    return count { it == '#'}
}

fun String.largestPossibleNextGroupSize(): Int {
    return substringBefore(".").count()
}

fun String.smallestPossibleNextGroupSize(): Int {
    return substringBefore("?").substringBefore(".").count()
}

fun String.isNextGroupMandatory(): Boolean {
    return substringBefore(".").contains("#")
}