package flashcards

data class Card(
    val term: String,
    val def: String,
    var errors: Int = 0
)

