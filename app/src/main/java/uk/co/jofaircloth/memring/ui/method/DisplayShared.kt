@file:OptIn(ExperimentalTextApi::class)

package uk.co.jofaircloth.memring.ui.method

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.IntSize
import uk.co.jofaircloth.memring.domain.PlaceNotationManager

internal fun DrawScope.drawVerticals(
    stage: Int,
    rowLocation: Int,
    spacingWidth: Float,
    spacingHeight: Float
) {
    for (place in 0 until stage) {
        drawLine(
            start = Offset(
                x = spacingWidth * place,
                y = 0F
            ),
            end = Offset(
                x = spacingWidth * place,
                y = spacingHeight * rowLocation
            ),
            color = Color.LightGray,
            alpha = 1.0f,
            cap = StrokeCap.Square,
            strokeWidth = 1F
        )
    }
}

internal fun DrawScope.drawNotation(
    notation: String,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    spacingHeight: Float
) {
    val fullNotation = PlaceNotationManager()
        .fullNotation(notation)
        .replace("-", "x")
        .split(".")

    val style = textStyle.copy(fontSize = textStyle.fontSize * 0.8, color = Color.Gray)
    val textLayoutResult = textMeasurer.measure(text = AnnotatedString(text = "2", spanStyle = SpanStyle(fontSize = textStyle.fontSize))) // A random number to get its width
    val textSize = textLayoutResult.size

    for ((idx, n) in fullNotation.withIndex()) {
        drawText(
            textMeasurer = textMeasurer,
            text = n,
            topLeft = Offset(
                x = -n.length * textSize.width.toFloat() - textSize.width.toFloat() * 1.7F,
                y = (spacingHeight * idx)
            ),
            style = style
        )
    }
}

internal fun DrawScope.drawLeadSeparator(
    stage: Int,
    textSize: IntSize,
    totalRows: Int,
    spacingWidth: Float,
    spacingHeight: Float
) {
    drawLine(
        start = Offset(x = -textSize.width / 2f, y = (totalRows - 1) * spacingHeight - (spacingHeight / 2)),
        end = Offset(x = spacingWidth * (stage - 1) + textSize.width / 2, y = (totalRows - 1) * spacingHeight - (spacingHeight / 2)),
        color = Color.DarkGray,
        alpha =  1.0f,
        cap = StrokeCap.Square,
        strokeWidth = 0.8f
    )
}

