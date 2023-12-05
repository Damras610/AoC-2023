package classes

data class Almanac(
    val seeds: Set<Long>,
    val seedRange: Set<LongRange>,
    val seedToSoil: Map<LongRange, LongRange>,
    val soilToFertilizer: Map<LongRange, LongRange>,
    val fertilizerToWater: Map<LongRange, LongRange>,
    val waterToLight: Map<LongRange, LongRange>,
    val lightToTemperature: Map<LongRange, LongRange>,
    val temperatureToHumidity: Map<LongRange, LongRange>,
    val humidityToLocation: Map<LongRange, LongRange>,
) {
    companion object {
        fun parse(inputFile: List<String>): Almanac {
            val almanacParts = mutableListOf<MutableList<String>>()
            var currentPartInFile = 0
            almanacParts.add(mutableListOf())

            inputFile.forEach {
                // When the line is a carriage return, we parse the next part of the file
                if (it.isBlank()) {
                    almanacParts.add(mutableListOf())
                    currentPartInFile += 1
                } else {
                    almanacParts[currentPartInFile].add(it)
                }
            }

            return Almanac(
                seeds = parseSeeds(almanacParts[0].first()),
                seedRange = parseSeedRanges(almanacParts[0].first()),
                seedToSoil = parseMap(almanacParts[1]),
                soilToFertilizer = parseMap(almanacParts[2]),
                fertilizerToWater = parseMap(almanacParts[3]),
                waterToLight = parseMap(almanacParts[4]),
                lightToTemperature = parseMap(almanacParts[5]),
                temperatureToHumidity = parseMap(almanacParts[6]),
                humidityToLocation = parseMap(almanacParts[7])
            )
        }

        private fun parseSeeds(inputLine: String): Set<Long> {
            return inputLine.substringAfter(":").trim().split(" ").map { it.toLong() }.toSet()
        }

        private fun parseSeedRanges(inputLine: String): Set<LongRange> {
            return inputLine.substringAfter(":").trim().split(" ").let {
                val seeds = mutableSetOf<LongRange>()
                for (i in 0 until it.size / 2) {
                    seeds.add(LongRange(it[i * 2].toLong(), it[i * 2].toLong() + it[i * 2 + 1].toLong() - 1))
                }
                seeds
            }
        }

        private fun parseMap(inputLines: List<String>): Map<LongRange, LongRange> {
            return inputLines.drop(1).map {
                val (destination, source, range) = it.split(" ").map { it.toLong() }
                LongRange(source, source + range) to LongRange(destination, destination + range)
            }.toMap().toSortedMap(compareBy { it.first })
        }
    }
}