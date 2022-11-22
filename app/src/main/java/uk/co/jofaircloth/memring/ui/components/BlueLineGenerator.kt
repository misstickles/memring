package uk.co.jofaircloth.memring.ui.components

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.co.jofaircloth.memring.domain.PlaceNotationManager
import uk.co.jofaircloth.memring.ui.theme.MemringTheme

data class BlueLineStyle(
    var color: Color = Color.Red,
    var colors: List<Color> = listOf(Color.Blue),
    var strokeWidth: Float = 2F
)

private const val TAG = "BlueLineGenerator"

// TODO refactor!!
@OptIn(ExperimentalTextApi::class)
@Composable
fun GenerateLine(
    modifier: Modifier = Modifier,
    method: List<List<String>>,
    forBells: List<String> = listOf("1", "5"), // this should only ever be len = 2
    treble: BlueLineStyle = BlueLineStyle(color = Color.Red, strokeWidth = 1F),
    workingBells: BlueLineStyle = BlueLineStyle(colors = listOf(Color.Green), strokeWidth = 2F),
    asOneLead: Boolean = false,
    fontSize: Int = 16,
    showText: Boolean = true,
    showVerticals: Boolean = true,
    showStartBells: Boolean = true,
    asMultiColumn: Boolean = true,      // TODO
    hideLinedNumbers: Boolean = false
) {
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(text = AnnotatedString(text = "2", spanStyle = SpanStyle(fontSize = fontSize.sp))) // A random number to get its width
    val textSize = textLayoutResult.size

    val spacingWidth = textSize.width * 1.4f
    val spacingHeight = textSize.height * .8f

    var currentRow: List<String>
    var currentBellIndex: Int
    var totalRows: Int

    val rowCount = method.count() * method[0].count() - method.count() + 1 // TODO: ??
    val stage: Int = method[0][0].count()

    val textStyle = TextStyle(fontSize = fontSize.sp, color = Color.Black)

    Box(modifier = Modifier
        .verticalScroll(rememberScrollState())
    ) {
        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .offset(x = 10.dp, y = 10.dp)
                .height((spacingHeight * rowCount / 2.5).dp)
        ) {
            if (showVerticals) {    // TODO Need to put this after - but also need to put it behind
                for (place in 0 until stage) {
                    drawLine(
                        start = Offset(
                            x = spacingWidth * place,
                            y = 0F
                        ),
                        end = Offset(
                            x = spacingWidth * place,
                            y = spacingHeight * (if (asOneLead) method[0].count() - 1 else rowCount - 1)
                        ),
                        color = Color.LightGray,
                        alpha = 1.0f,
                        cap = StrokeCap.Square,
                        strokeWidth = 1F
                    )
                }
            }

            for (bell in forBells) {
                if (bell.toInt() > stage) continue

                var previousBellIndex = bell.toInt() - 1
                totalRows = 1

                for ((leadId, lead) in method.withIndex()) {
                    val color: Color = if (asOneLead) {
                        if (bell == "1") treble.color else workingBells.colors[(leadId % workingBells.colors.count())]
                    } else {
                        if (bell == "1") treble.color else workingBells.colors[0]
                    }

                    val strokeWidth: Float = if (bell == "1") treble.strokeWidth else workingBells.strokeWidth

                    // label bell start positions
                    if ((bell != "1") and !asOneLead) {
                        val offset = Offset(x = spacingWidth * (stage + 0.5f), y = spacingHeight * (totalRows - 1) - textSize.height / 2)

                        drawCircle(
                            color = Color.DarkGray,
                            style = Stroke(),
                            radius = ((fontSize + 5).toFloat()),
                            center = Offset(
                                x = offset.x + (textSize.width / 2),
                                y = offset.y + (textSize.height / 2))
                        )

                        val startBell = (lead[0].indexOf(bell) + 1).toString()
                        Log.d(TAG, "Bell Length: $startBell : ${startBell.length}")

                        drawText(
                            textMeasurer = textMeasurer,
                            text = startBell,
                            topLeft = Offset(
                                x = (spacingWidth * (stage + 0.5f) - ((startBell.length - 1f) * textSize.width * 0.8f / 2)),
                                y = spacingHeight * (totalRows - 1) - textSize.height * 0.8f / 2),
                            style = textStyle.copy(fontSize = textStyle.fontSize * 0.8)
                        )
                    }

                    // first row repeats rounds + previous row in each lead, start @1 removes it
                    for ((rowId, row) in lead.withIndex()) {
                        if (rowId == 0) continue

                        currentRow = row.chunked(1)
                        currentBellIndex = currentRow.indexOf(bell)

                        drawLine(
                            start = Offset(
                                x = spacingWidth * previousBellIndex,
                                y = spacingHeight * (if (asOneLead) rowId - 1 else totalRows - 1)
                            ),
                            end = Offset(
                                x = spacingWidth * currentBellIndex,
                                y = spacingHeight * (if (asOneLead) rowId else totalRows)
                            ),
                            color = color,
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Round
                        )

                        previousBellIndex = currentBellIndex
                        totalRows++
                    }

                    // lead separator
                    if (showStartBells and !asOneLead) {
                        Log.d(TAG, "Showing start bells")
                        drawLine(
                            start = Offset(x = -textSize.width / 2f, y = (totalRows - 1) * spacingHeight - (spacingHeight / 2)),
                            end = Offset(x = spacingWidth * (stage - 1) + textSize.width / 2, y = (totalRows - 1) * spacingHeight - (spacingHeight / 2)),
                            color = Color.DarkGray,
                            alpha =  1.0f,
                            cap = StrokeCap.Square,
                            strokeWidth = 0.8f
                        )
                    }
                }
            }

            if (showText) {
                totalRows = 0

                // first row of rounds is not actually part of the method...
                for ((place, bell) in method[0][0].chunked(1).withIndex()) {
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

                for ((leadId, lead) in method.withIndex()) {
                    if (asOneLead and (leadId > 0)) continue;

                    for ((rowId, row) in lead.withIndex()) {
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
}

//@Preview(
//    showBackground = true,
//    name = "Method Light",
//    widthDp = 400, heightDp = 1000)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Method Dark",
    widthDp = 400, heightDp = 1000)
@Composable
fun DisplayBlueLinePreview() {
    val method: List<List<String>>
//    method = PlaceNotationManager().generateRows("5.1.5.1.5.1.5.1.5.1", 5) // plain hunt
//    method = PlaceNotationManager().generateRows("5.1.5.1.5,125", 5) // plain bob doubles
//    method = PlaceNotationManager().generateRows("-36-14-12-36.14-12.56,12", 6) // surfleet minor
//    method = PlaceNotationManager().generateRows("-38-14-58-16-14-38-34-18,12", 8) // rutland major
//    method = PlaceNotationManager().generateRows("3.1.7.3.1.3,1", 7) // stedman triples
//    method = BlueLine().generateRows("3,1.5.1.7.1.7.1", 7) // single oxford bob triples
//    method = BlueLine().generateRows("3,1.5.1.7.3.7.5", 7) // double oxford bob triples
    method = PlaceNotationManager().generateRows("-5D-14.5D-5D.36.14-7D.58.16-9D.70.18-ED.9T.10-AD.EB.1T-1T.AD-1T-1D,1D", 16) // bristol s sixteen

    Log.d(TAG, "Preview method: $method")

    MemringTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column() {
                Text(
                    text = "Oooh, a method",
                    color = MaterialTheme.colorScheme.onBackground,
//                    style = MaterialTheme.typography.titleLarge
                )
                GenerateLine(
                    modifier = Modifier
                        .background(color = Color.White),
                    method = method,
                    forBells = listOf("1", "3"),
                    treble = BlueLineStyle(color = Color.Red, strokeWidth = 2F),
                    workingBells = BlueLineStyle(colors = listOf(Color.Blue), strokeWidth = 4F),
                    asOneLead = true,
                    fontSize = 10,
                    showText = true,
                    showVerticals = true,
                    showStartBells = true,
                    asMultiColumn = true,
                    hideLinedNumbers = true
                )
            }
        }
    }
}