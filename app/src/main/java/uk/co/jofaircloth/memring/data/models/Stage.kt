package uk.co.jofaircloth.memring.data.models

enum class Stage(
    val number: Int,
    val description: String
) {
    None(number = 0, description = ""),
    Twos(number = 2, description = ""),
    Singles(number = 3, description = ""),
    Minimus(number = 4, description = ""),
    Doubles(number = 5, description = ""),
    Minor(number = 6, description = ""),
    Triples(number = 7, description = ""),
    Major(number = 8, description = ""),
    Caters(number = 9, description = ""),
    Royal(number = 10, description = ""),
    Cinques(number = 11, description = ""),
    Maximus(number = 12, description = ""),
    Sextuples(number = 13, description = ""),
    Fourteen(number = 14, description = ""),
    Septuples(number = 15, description = ""),
    Sixteen(number = 16, description = ""),
    Octuples(number = 17, description = ""),
    Eighteen(number = 18, description = ""),
    Nineteen(number = 19, description = ""),
    Twenty(number = 20, description = ""),
    TwentyOne(number = 21, description = ""),
    TwentyTwo(number = 22, description = "")
}

fun Int?.asStage() = when (this) {
    null -> Stage.None
    else -> Stage.values()
        .firstOrNull {
            type -> type.number == this
        } ?: Stage.None
}