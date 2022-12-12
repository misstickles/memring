package uk.co.jofaircloth.memring.data.models

enum class CallSymbol(
    symbol: String,
    name: String
) {
    Bob("-", "Bob"),
    Single("s", "Single"),
    Extreme("e", "Extreme"),
    Omit("o", "Omit"),
    Double("d", "Double"),
    BobSingle("b", "Bob Single"),
    BigBob("x", "Big Bob")
}