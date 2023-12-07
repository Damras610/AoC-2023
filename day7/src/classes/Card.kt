package classes

enum class Card {
    CARD_JOKER,
    CARD_2,
    CARD_3,
    CARD_4,
    CARD_5,
    CARD_6,
    CARD_7,
    CARD_8,
    CARD_9,
    CARD_T,
    CARD_J,
    CARD_Q,
    CARD_K,
    CARD_A;

    override fun toString(): String {
        return name.removePrefix("CARD_").replace("JOKER", "J")
    }
}