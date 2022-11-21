package uk.co.jofaircloth.memring.ui.components

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
    showVerticals: Boolean = true,
    showStartBells: Boolean = true,
    asMultiColumn: Boolean = true,      // TODO
    hideLinedNumbers: Boolean = false   // TODO
) {
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(text = AnnotatedString(text = "2", spanStyle = SpanStyle(fontSize = fontSize.sp))) // A random number to get its width
    val textSize = textLayoutResult.size
    val spacingWidth = textSize.width * 1.4f
    val spacingHeight = textSize.height * .8f

    var currentRow: List<String>
    var currentBellIndex: Int
    var totalRows = 1
    val stage: Int = method[0][0].count()

    var shownPlaceStarts = false

    Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Canvas(
            modifier = Modifier
                .border(3.dp, Color.Green)
                .fillMaxWidth()
                .height(20000.dp)
//                .width((spacingWidth * (stage - 3)).dp)
////                .matchParentSize()
////                .background(color = Color.Yellow)
                .offset(x = 10.dp, y = 10.dp)
////                .requiredSize(
////                    width = (spacingWidth * (stage - 3)).dp,
////                    height = (spacingHeight * method.count() * (method[0].count() - 1) * 0.4).dp
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
                            y = spacingHeight * (if (asOneLead) method[0].count() else method.count() * method[0].count())
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

                    // label bell start positions - and only once!
                    if (showStartBells and !asOneLead and !shownPlaceStarts) {
                        drawLine(
                            start = Offset(x = 0f, y = totalRows * spacingHeight - (spacingHeight / 2)),
                            end = Offset(x = spacingWidth * (stage - 1), y = totalRows * spacingHeight - (spacingHeight / 2)),
                            color = Color.DarkGray,
                            alpha =  1.0f,
                            cap = StrokeCap.Square,
                            strokeWidth = 0.8f
                        )

                        drawArc(
                            color = Color.LightGray,
                            startAngle = 0f,
                            sweepAngle = 360f,
                            useCenter = false,
                            style = Stroke(),
                            size = Size(width = 60f, height = 60f),
                            topLeft = Offset(x = spacingWidth * (stage - 0.8f), y = totalRows * spacingHeight - (spacingHeight / 2))
                        )

//                        drawText(
//                            textMeasurer = textMeasurer,
//                            text = (previousBellIndex + 1).toString(),
//                            topLeft = Offset(x = spacingWidth * (stage - 0.5f), y = spacingHeight * totalRows)
//                        )
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
                            strokeWidth = strokeWidth
                        )

                        previousBellIndex = currentBellIndex
                        totalRows++
                    }
                }

                shownPlaceStarts = true // set to only show bell starts once (or it's done for each bell lined)
            }
//
//            Log.d(TAG, "Size: $textSize")
//
            val textStyle = TextStyle(fontSize = fontSize.sp, color = Color.Black)
            totalRows = 0

            // first row of rounds is not actually part of the method...
            for ((place, bell) in method[0][0].chunked(1).withIndex()) {
                Log.d(TAG, "First row: $place, $bell")
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

                    Log.d(TAG, "Row: $rowId / $row / ${lead.count()}")

                    for((place, bell) in row.chunked(1).withIndex()) {
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

//@Composable
//fun DisplayMethodText(
//    modifier: Modifier = Modifier,
//    method: List<List<String>>,
//    letterSpacing: TextUnit = 8.sp,
//    lineHeight: TextUnit = 8.sp
//) {
//    Column(
//        modifier = modifier
//    ) {
//        // first row of rounds is not actually part of the method...
//        Text(
//            text = method[0][0],
//            letterSpacing = letterSpacing,
//            lineHeight = lineHeight,
//            color = Color.Black
//        )
//
//        for (lead in method) {
//            for (row in 1 until lead.count()) {
//                Text(
//                    text = lead[row],
//                    letterSpacing = letterSpacing,
//                    lineHeight = lineHeight,
//                    color = Color.Black
//                )
//            }
//        }
//    }
//}

//    @Preview(showBackground = true, widthDp = 400, heightDp = 600)
//    @Composable
//    fun DisplayMethodTextPreview() {
//        val method = BlueLine().generateRows("5.1.5.1.5,125", 5)
//        MemringTheme { DisplayMethodText(method = method, letterSpacing = 8.sp, lineHeight = 50.sp) }
//    }

@Preview(showBackground = true, widthDp = 400, heightDp = 500)
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
        GenerateLine(
            method = method,
            forBells = listOf("1", "4"),
            treble = BlueLineStyle(color = Color.Red, strokeWidth = 2F),
            workingBells = BlueLineStyle(colors = listOf(Color.Blue), strokeWidth = 4F),
            asOneLead = false,
            fontSize = 12,
            showVerticals = true,
            showStartBells = true,
            asMultiColumn = true
        )
    }
}