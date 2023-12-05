import classes.Almanac
import java.io.File
import kotlin.math.max
import kotlin.math.min


fun main() {
    val inputLines = File("day5/resources/input.txt").readLines()

    val almanac = Almanac.parse(inputLines)

    val minimumLocation = almanac.seedRange.toList().let { reduce(it) }
        .flatMap { source -> getDestination(almanac.seedToSoil, source) }.let { reduce(it) }
        .flatMap { source -> getDestination(almanac.soilToFertilizer, source) }.let { reduce(it) }
        .flatMap { source -> getDestination(almanac.fertilizerToWater, source) }.let { reduce(it) }
        .flatMap { source -> getDestination(almanac.waterToLight, source) }.let { reduce(it) }
        .flatMap { source -> getDestination(almanac.lightToTemperature, source) }.let { reduce(it) }
        .flatMap { source -> getDestination(almanac.temperatureToHumidity, source) }.let { reduce(it) }
        .flatMap { source -> getDestination(almanac.humidityToLocation, source) }.let { reduce(it) }
        .minOf { it.first }

    println(minimumLocation)
}

/**
 * Return the destination from a map and a source range
 */
fun getDestination(currentMap: Map<LongRange, LongRange>, source: LongRange): List<LongRange> {
    val destination = mutableListOf<LongRange>()
    var hasIntersected = false
    currentMap.forEach {

        // The source intersects with a destination chunk
        if (source.first <= it.key.last && source.last >= it.key.first) {
            hasIntersected = true
            val intersectionRange = LongRange(max(source.first, it.key.first), min(source.last, it.key.last))
            val destinationStart = it.value.first + (intersectionRange.first - it.key.first)
            val destinationEnd = destinationStart + intersectionRange.size()
            destination.add(LongRange(destinationStart, destinationEnd))

            // HANDLE the remaining that is not part of the destination chunk.
            // * 1st case: the source starts before the destination chunk
            // *** source:     |----------------|
            // *** currentMap:         |------------|
            if (source.first < it.key.first) {
                destination.addAll(getDestination(currentMap, LongRange(source.first, it.key.first - 1)))
            }
            // * 2nd case: the source ends after the destination chunk
            // *** source:           |----------------|
            // *** currentMap: |--------------|
            if (source.last > it.key.last) {
                destination.addAll(getDestination(currentMap, LongRange(it.key.last + 1, source.last)))
            }
        }
    }

    // The source does not intersect any of the destination chunks, then destination = source
    if (!hasIntersected)
        destination.add(source)

    return destination
}

/**
 * Merge the overlapping or directly following ranges.
 * For example, "1..3" and "2..4" are merged into a single 1..4 range.
 * Similarly, "1..3" and "4..6" are merged into a single 1..6 range.
 */
fun reduce(source: List<LongRange>): Set<LongRange> {
    val reduced = mutableSetOf<LongRange>()
    source.sortedBy { it.first }
        .forEach {
            if (reduced.isNotEmpty() && reduced.last().last + 1 >= it.first) {
                val newRange = LongRange(reduced.last().first, it.last)
                reduced.remove(reduced.last())
                reduced.add(newRange)
            } else {
                reduced.add(it)
            }
        }
    return reduced
}

fun LongRange.size(): Long {
    return last - start
}