package classes

data class Game(
    val id: Int,
    val rounds: List<Round>
) {
    companion object {
        fun parse(inputString: String): Game {
            val gameId = inputString.substringAfter("Game ").substringBefore(":").toInt()
            val rounds = inputString.substringAfter(':').split(";")
                .map {
                    Round.parse(it)
                }
            return Game(gameId, rounds)
        }
    }
}