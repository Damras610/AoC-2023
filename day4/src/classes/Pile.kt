package classes

data class Pile(
    private val footprints: Set<Card>,
) {
    // The set of the cards associated by IDs
    private val footprintsMap = footprints.associateBy { it.id }
    // The maps associating the IDs of each card and the number of cards with this ID
    // One map is for the cards to process and the other is for the processed cards.
    private val toProcess = footprints.map { it.id to 1 }.toMap().toMutableMap()
    private val processed = footprints.map { it.id to 0 }.toMap().toMutableMap()

    val pileSize: Int
        get() = toProcess.map { it.value }.sum() + processed.map { it.value }.sum()

    /**
     * Process all the cards in the ToProcess map
     */
    fun process() {
        // Process until there is no cards to process, i.e. run the process as long as any ID as any cards left to process
        while (toProcess.any { it.value > 0 }) {
            // Get the cards to process
            val idToProcess = toProcess.firstNotNullOf { if (it.value > 0) it.key else null }
            val cardUnderProcessing = footprintsMap[idToProcess]!!

            // Get the IDs of the cards to add and add them to the toProcess map.
            footprintsMap.filter {
                cardUnderProcessing.goodNumbers.isNotEmpty() &&
                        it.key > cardUnderProcessing.id &&
                        it.key <= cardUnderProcessing.id + cardUnderProcessing.goodNumbers.size
            }.forEach {
                // To add a cards, increment the number of cards of the ID to add
                toProcess[it.key] = toProcess[it.key]!! + 1
            }
            // Add the processedId to the processed pile
            processed[idToProcess] = processed[idToProcess]!! + 1
            // Remove the processedId from the toProcess pile
            toProcess[idToProcess] = toProcess[idToProcess]!! - 1
        }
    }
}