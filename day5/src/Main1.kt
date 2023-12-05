import classes.Almanac
import java.io.File

fun main() {
    val inputLines = File("day5/resources/input.txt").readLines()

    val almanac = Almanac.parse(inputLines)

    val minimumLocation = almanac.seeds
        .asSequence()
        .map { getDestination(almanac.seedToSoil, it) }
        .map { getDestination(almanac.soilToFertilizer, it) }
        .map { getDestination(almanac.fertilizerToWater, it) }
        .map { getDestination(almanac.waterToLight, it) }
        .map { getDestination(almanac.lightToTemperature, it) }
        .map { getDestination(almanac.temperatureToHumidity, it) }
        .map { getDestination(almanac.humidityToLocation, it) }
        .toList()
        .min()

    println(minimumLocation)
}

fun getDestination(currentMap: Map<LongRange, LongRange>, source: Long): Long {
    return currentMap.firstNotNullOfOrNull { if (source >= it.key.first && source <= it.key.last) it.value.first + (source - it.key.first) else null } ?: source
}