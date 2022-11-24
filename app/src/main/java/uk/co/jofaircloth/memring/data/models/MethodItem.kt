package uk.co.jofaircloth.memring.data.models

import androidx.compose.runtime.Immutable

@Immutable
data class MethodItem (
    val id: String,
    val method: Method? = Method(mutableListOf(mutableListOf(""))),
    val name: String? = "",
    val stage: Stage? = Stage.None,
    val classification: Classification? = Classification(),
    val notation: String? = "",
    val leadLength: Int? = 0,
    val huntBellPath: String? = "",
    val huntBells: Int? = 1,
    val leadEndNotation: String? = "",
) {
    override fun toString(): String {
        return super.toString()
    }
}

@Immutable
data class Method (
    var generated: MutableList<MutableList<String>> = mutableListOf(mutableListOf("")),
)

@Immutable
data class Classification (
    val isLittle: Boolean = false,
    val isPlain: Boolean = false,
    val isTrebleDodging: Boolean = false
)
