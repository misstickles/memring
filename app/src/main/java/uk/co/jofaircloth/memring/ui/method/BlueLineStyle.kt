package uk.co.jofaircloth.memring.ui.method

import androidx.compose.ui.graphics.Color

data class BlueLineStyle(
    var color: Color = Color.Red,
    var colors: List<Color> = listOf(Color.Blue),
    var strokeWidth: Float = 2F
)
