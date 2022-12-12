package uk.co.jofaircloth.memring.ui.method

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.co.jofaircloth.memring.data.entities.MethodPropertyEntity
import uk.co.jofaircloth.memring.data.models.BobSingle
import uk.co.jofaircloth.memring.data.models.CallSymbol
import uk.co.jofaircloth.memring.domain.BobSingleManager
import uk.co.jofaircloth.memring.domain.MethodManager
import uk.co.jofaircloth.memring.domain.PlaceNotationManager
import uk.co.jofaircloth.memring.ui.theme.MemringTheme

private const val TAG = "BobSingleManager"

@OptIn(ExperimentalTextApi::class)
@Composable
fun DisplayBobSingle(
    methodProperty: MethodPropertyEntity,
    call: String,
    modifier: Modifier = Modifier,
    treble: BlueLineStyle = BlueLineStyle(color = Color.Red, strokeWidth = 2F),
    workingBells: BlueLineStyle = BlueLineStyle(colors = listOf(Color.Blue, Color.Green, Color.Magenta, Color.Cyan, Color.Yellow), strokeWidth = 4F),
    fontSize: Int = 16,
    showText: Boolean = true,
    showVerticals: Boolean = true,
    hideLinedNumbers: Boolean = false
) {
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(text = AnnotatedString(text = "2", spanStyle = SpanStyle(fontSize = fontSize.sp))) // A random number to get its width
    val textSize = textLayoutResult.size

    val spacingWidth = textSize.width * 1.4f
    val spacingHeight = textSize.height * .8f

    val textStyle = TextStyle(fontSize = fontSize.sp, color = Color.Black)

    var currentBellIndex: Int
    var totalRows: Int

    val stage = methodProperty.stage

    val rowCount = 8
    val (callRows, affected) = BobSingleManager().generate(methodProperty, call, rowCount)
    var currentRow: List<String>

    Box(modifier = Modifier
        .verticalScroll(rememberScrollState())
    ) {
        Canvas(
            modifier = modifier
                .offset(x = 10.dp, y = 10.dp)
                .width((spacingWidth * (stage - 1)).dp)
                .height((spacingHeight * rowCount / 2.5).dp)
        ) {
            if (showVerticals) {
                for (place in 0 until stage) {
                    drawLine(
                        start = Offset(
                            x = spacingWidth * place,
                            y = 0F
                        ),
                        end = Offset(
                            x = spacingWidth * place,
                            y = spacingHeight * (rowCount - 1)
                        ),
                        color = Color.LightGray,
                        alpha = 1.0f,
                        cap = StrokeCap.Square,
                        strokeWidth = 1F
                    )
                }
            }

            val forBells = affected.chunked(1)

            for ((bellId, bell) in forBells.withIndex()) {
                var previousBellIndex = callRows[0].indexOf(forBells[bellId])
                totalRows = 1
Log.d(TAG, "BELL ID $bellId")
                val color = if (bell == "1") treble.color else workingBells.colors[(bellId % workingBells.colors.count())]

                val strokeWidth: Float = if (bell == "1") treble.strokeWidth else workingBells.strokeWidth

                for ((rowId, row) in callRows.withIndex()) {
                    if (rowId == 0) continue

                    currentRow = row.chunked(1)
                    currentBellIndex = currentRow.indexOf(bell)

                    drawLine(
                        start = Offset(
                            x = spacingWidth * previousBellIndex,
                            y = spacingHeight * (totalRows - 1)
                        ),
                        end = Offset(
                            x = spacingWidth * currentBellIndex,
                            y = spacingHeight * totalRows
                        ),
                        color = color,
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round
                    )

                    previousBellIndex = currentBellIndex
                    totalRows++
                }

                // lead separator
                drawLine(
                    start = Offset(x = -textSize.width / 2f, y = (rowCount / 2) * spacingHeight - (spacingHeight / 2)),
                    end = Offset(x = spacingWidth * (stage - 1) + textSize.width / 2, y = (rowCount / 2) * spacingHeight - (spacingHeight / 2)),
                    color = Color.DarkGray,
                    alpha =  1.0f,
                    cap = StrokeCap.Square,
                    strokeWidth = 0.8f
                )
            }

            if (showText) {
                totalRows = 0

                // TODO - calculate the ones affected (probably higher up)

                for ((place, bell) in callRows[0].chunked(1).withIndex()) {
                    if (hideLinedNumbers and forBells.contains(bell)) continue

                    drawText(
                        textMeasurer = textMeasurer,
                        text = bell,
                        topLeft = Offset(
                            x = (spacingWidth * place) - (textSize.width / 2),
                            y = (spacingHeight * totalRows) - (textSize.height / 2)
                        ),
                        style = textStyle
                    )
                }

                for ((rowId, row) in callRows.withIndex()) {
                    if (rowId == 0) continue

                    totalRows++

                    for((place, bell) in row.chunked(1).withIndex()) {
                        if (hideLinedNumbers and forBells.contains(bell)) continue

                        drawText(
                            textMeasurer = textMeasurer,
                            text = bell,
                            topLeft = Offset(
                                x = (spacingWidth * place) - (textSize.width / 2),
                                y = (spacingHeight * totalRows) - (textSize.height / 2)
                            ),
                            style = textStyle
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Method Dark",
    widthDp = 150, heightDp = 200)
@Composable
fun DisplayBobPreview() {
//    val method = MethodPropertyEntity(notation = "5.1.5.1.5.1.5.1.5.1", stage = 5, title = "Plain hunt", id = "")
//    val method = MethodPropertyEntity(notation = "5.1.5.1.5,125", stage = 5, title = "Plain bob doubles", id = "")
    val method = MethodPropertyEntity(notation = "-36-14-12-36.14-12.56,12", stage = 6, title = "Surfleet minor", id = "")
//    val method = MethodPropertyEntity(notation = "-38-14-58-16-14-38-34-18,12", stage = 8, title = "Rutland Surprise Major", id = "")
//    val method = MethodPropertyEntity(notation = "3.1.7.3.1.3,1", stage = 7, title = "Stedman triples", id = "")
//    val method = MethodPropertyEntity(notation = "3,1.5.1.7.1.7.1", stage = 7, title = "Single oxford bob triples", id = "")
//    val method = MethodPropertyEntity(notatio = "3,1.5.1.7.3.7.5", stage = 7, title = "Double oxford bob triples", id = "")
//    val method = MethodPropertyEntity(notation = "-5D-14.5D-5D.36.14-7D.58.16-9D.70.18-ED.9T.10-AD.EB.1T-1T.AD-1T-1D,1D", stage = 16, title = "Bristol Surprise Sixteen", id = "")

    MemringTheme {
        DisplayBobSingle(
            methodProperty = method,
            showVerticals = true,
            hideLinedNumbers = false,
            call = BobSingleManager().calls(method).bob ?: ""
        )
    }
}
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Method Dark",
    widthDp = 150, heightDp = 200)
@Composable
fun DisplaySinglePreview() {
//    val method = MethodPropertyEntity(notation = "5.1.5.1.5.1.5.1.5.1", stage = 5, title = "Plain hunt", id = "")
//    val method = MethodPropertyEntity(notation = "5.1.5.1.5,125", stage = 5, title = "Plain bob doubles", id = "")
    val method = MethodPropertyEntity(notation = "-36-14-12-36.14-12.56,12", stage = 6, title = "Surfleet minor", id = "")
//    val method = MethodPropertyEntity(notation = "-38-14-58-16-14-38-34-18,12", stage = 8, title = "Rutland Surprise Major", id = "")
//    val method = MethodPropertyEntity(notation = "3.1.7.3.1.3,1", stage = 7, title = "Stedman triples", id = "")
//    val method = MethodPropertyEntity(notation = "3,1.5.1.7.1.7.1", stage = 7, title = "Single oxford bob triples", id = "")
//    val method = MethodPropertyEntity(notatio = "3,1.5.1.7.3.7.5", stage = 7, title = "Double oxford bob triples", id = "")
//    val method = MethodPropertyEntity(notation = "-5D-14.5D-5D.36.14-7D.58.16-9D.70.18-ED.9T.10-AD.EB.1T-1T.AD-1T-1D,1D", stage = 16, title = "Bristol Surprise Sixteen", id = "")

    MemringTheme {
        DisplayBobSingle(
            methodProperty = method,
            showVerticals = true,
            hideLinedNumbers = false,
            call = BobSingleManager().calls(method).single ?: ""
        )
    }
}