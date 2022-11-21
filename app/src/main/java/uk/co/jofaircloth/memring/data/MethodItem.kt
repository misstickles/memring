package uk.co.jofaircloth.memring.data

import androidx.compose.runtime.Immutable

@Immutable
data class MethodItem (
    val id: String,
    val huntBells: Int = 1,
    val leadEndNotation: String = "",
    val method: Method,
    val name: String = "",
    val notation: String = "",
    val stage: Stage = Stage.None,
)

@Immutable
data class Method (
    var generated: MutableList<MutableList<String>> = mutableListOf(mutableListOf("")),
)
