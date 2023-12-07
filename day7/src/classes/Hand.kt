package classes

import java.lang.Exception

data class Hand(
    val cards: List<Card>,
    val bid: Int
): Comparable<Hand> {
    companion object {
        fun parse(inputLine: String, jokerVariant: Boolean = false): Hand {
            return Hand(
                cards = inputLine.take(5).toCharArray()
                    .map { Card.valueOf("CARD_$it") }
                    .map { if (jokerVariant && it == Card.CARD_J) Card.CARD_JOKER else it },
                bid = inputLine.substringAfter(" ").toInt()
            )
        }
    }

    /**
     * The distinct cards except jokers
     */
    private val distinctCards = cards.distinct().filter { it != Card.CARD_JOKER }

    /**
     * The type of hand
     */
    private val type: HandType
    init {
        type = setType()
    }

    /**
     * Set the type of hand depending on the held cards
     */
    private fun setType() : HandType {
        return when {
            isFiveOf() -> HandType.FIVE_OF
            isFourOf() -> HandType.FOUR_OF
            isFullHouse() -> HandType.FULL_HOUSE
            isThreeOf() -> HandType.THREE_OF
            isTwoPair()  -> HandType.TWO_PAIR
            isOnePair() -> HandType.ONE_PAIR
            isHighCard() -> HandType.HIGH_CARD
            else -> throw Exception("Error in setType() with cards ${cards.joinToString()}")
        }
    }

    private fun isFiveOf() = distinctCards.isEmpty() || distinctCards.count() == 1
    private fun isFourOf() = distinctCards.count() == 2 && (countWithJoker(distinctCards[0]) == 4 || countWithJoker(distinctCards[1]) == 4)
    private fun isFullHouse() = distinctCards.count() == 2 && (countWithJoker(distinctCards[0]) == 3 || countWithJoker(distinctCards[1]) == 3)
    private fun isThreeOf() = distinctCards.count() == 3 && (countWithJoker(distinctCards[0]) == 3 || countWithJoker(distinctCards[1]) == 3  || countWithJoker(distinctCards[2]) == 3)
    private fun isTwoPair() = distinctCards.count() == 3 && (countWithJoker(distinctCards[0]) == 2 || countWithJoker(distinctCards[1]) == 2)
    private fun isOnePair() = distinctCards.count() == 4
    private fun isHighCard() = distinctCards.count() == 5

    /**
     * Count the number of occurrence in the hand of a given card (including jokers)
     */
    private fun countWithJoker(card: Card): Int = cards.count { it == card || it == Card.CARD_JOKER }

    /**
     * Compare the card with another.
     */
    override fun compareTo(other: Hand) =
        compareValuesBy(this, other,
            { it.type },
            { it.cards[0] },
            { it.cards[1] },
            { it.cards[2] },
            { it.cards[3] },
            { it.cards[4] },
            { it.cards[5] }
    )

    override fun toString(): String = "${javaClass.simpleName}(cards=${cards.joinToString(prefix = "[", postfix = "]")}, bid=$bid, type=$type)"
}