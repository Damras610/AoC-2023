package classes

data class Round(
    val red: Int,
    val blue: Int,
    val green: Int
) {
    companion object {
        fun parse(inputString: String): Round {
            var numberOfRedCubes = 0
            var numberOfBlueCubes = 0
            var numberOfGreenCubes = 0
            inputString.split(",").map {
                it.trim()
            }.forEach {
                if (it.endsWith("red"))
                    numberOfRedCubes = it.substringBefore(" ").toInt()
                if (it.endsWith("blue"))
                    numberOfBlueCubes = it.substringBefore(" ").toInt()
                if (it.endsWith("green"))
                    numberOfGreenCubes = it.substringBefore(" ").toInt()
            }
            return Round(
                red = numberOfRedCubes,
                green = numberOfGreenCubes,
                blue = numberOfBlueCubes
            )
        }
    }
}