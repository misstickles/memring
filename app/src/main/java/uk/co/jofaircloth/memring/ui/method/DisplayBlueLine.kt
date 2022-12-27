// TODO Blue line separator moved too low when using extension method

@file:OptIn(ExperimentalTextApi::class)

package uk.co.jofaircloth.memring.ui.method

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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.co.jofaircloth.memring.data.entities.MethodPropertyEntity
import uk.co.jofaircloth.memring.data.models.BellNames
import uk.co.jofaircloth.memring.data.models.Method
import uk.co.jofaircloth.memring.domain.MethodManager
import uk.co.jofaircloth.memring.ui.theme.MemringTheme
import kotlin.math.max

private const val TAG = "PlaceNotationManager"

data class BlueLineUiModel(
    val methodProperty: MethodPropertyEntity,
    val modifier: Modifier = Modifier,
    val forBells: List<String> = listOf("1", "4"), // this should only ever be len = 2
    val treble: BlueLineStyle = BlueLineStyle(color = Color.Red, strokeWidth = 2F),
    val workingBells: BlueLineStyle = BlueLineStyle(colors = listOf(Color.Blue), strokeWidth = 4F),
    val asOneLead: Boolean = false,
    val fontSize: Int = 16,
    val showText: Boolean = true,
    val showVerticals: Boolean = true,
    val showStartBells: Boolean = true,
    val showNotation: Boolean = true,
    val showLinedNumbers: Boolean = false,
    val asMultiColumn: Boolean = true,      // TODO
    val widthRatio: Int = 5, // 1 - 5 only // TODO guard 1 - 5, inc
    val heightRatio: Int = 5 // 1 - 5 only // TODO guard 1 - 5, inc
)

// TODO refactor!!
// TODO suspend displayblueline
@Composable
fun DisplayBlueLine(
    input: BlueLineUiModel
) {
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(
        text = AnnotatedString(text = "2"),
        style = TextStyle(fontSize = input.fontSize.sp)) // A random number to get its width
    val textSize = textLayoutResult.size
    val textStyle = TextStyle(fontSize = input.fontSize.sp, color = Color.DarkGray)

    val spacingWidth = textSize.width * 1.4f * input.widthRatio * 0.25f // 1 = 0.25; 4 = 1.0
    val spacingHeight = textSize.height * .8f * input.heightRatio * 0.2f // 1 = 0.2; 5 = 1.0

    var currentRow: List<String>
    var currentBellIndex: Int
    var totalRows: Int

    val method = MethodManager().generate(input.methodProperty)
    val stage = input.methodProperty.stage

    val rowCount = (method.count() * (method[0].count() - 1)) // # leads * rows in lead - repeat per lead

    Box(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(start = 40.dp, top = 10.dp)
    ) {
        val width = with(LocalDensity.current) {
            spacingWidth.toDp() * (stage + 2)
        }
        val height = with(LocalDensity.current) {
            (spacingHeight.toDp() * rowCount) + (textSize.height / 2).toDp()
        }

        Log.d(TAG, "W: $width / H: $height / T: $textSize")

        Canvas( // TODO fix the width / height!
            modifier = input.modifier
                .requiredSize(
                    width = width,
                    height = height
                )
        ) {
            try {
            if (input.showVerticals) {
                this.drawVerticals(
                    stage = stage,
                    rowLocation = if (input.asOneLead) method[0].count() - 1 else rowCount - 1,
                    spacingWidth = spacingWidth,
                    spacingHeight = spacingHeight
                )
            }

            for ((bellId, bell) in method[0][0].chunked(1).withIndex()) {
                val bellInt = BellNames.indexOf(bell.toCharArray()[0])

                if (bellInt > stage) continue

                var previousBellIndex = bellInt
                totalRows = 0

                if (input.showText) {
                    drawText(
                        textMeasurer = textMeasurer,
                        text = bell,
                        topLeft = Offset(
                            x = (spacingWidth * bellId) - (textSize.width / 2),
                            y = (spacingHeight * totalRows) - (textSize.height / 2)
                        ),
                        style = textStyle
                    )
                }

                val color: Color = if (input.asOneLead) {
                    if (bell == "1") input.treble.color else input.workingBells.colors[(input.workingBells.colors.count() + bellId - 1) % input.workingBells.colors.count()]
                } else {
                    if (bell == "1") input.treble.color else input.workingBells.colors[0]
                }

                val strokeWidth: Float = if (bell == "1") input.treble.strokeWidth else input.workingBells.strokeWidth

                for (lead in method) {
                    if ((bell != "1") and !input.asOneLead and (input.forBells.contains(bell))) {
                        this.drawStartPositions(
                            bell = bell,
                            lead = lead,
                            stage = stage,
                            textMeasurer = textMeasurer,
                            textStyle = textStyle,
                            textSize = textSize,
                            fontSize = input.fontSize,
                            totalRows = totalRows + 1,
                            spacingWidth = spacingWidth,
                            spacingHeight = spacingHeight
                        )
                    }

                    for ((rowId, row) in lead.withIndex()) {
                        if (rowId == 0) continue

                        currentRow = row.chunked(1)
                        currentBellIndex = currentRow.indexOf(bell)

                        if (input.forBells.contains(bell)) {
                            drawLine(
                                start = Offset(
                                    x = spacingWidth * previousBellIndex,
                                    y = spacingHeight * (if (input.asOneLead) rowId - 1 else totalRows)
                                ),
                                end = Offset(
                                    x = spacingWidth * currentBellIndex,
                                    y = spacingHeight * (if (input.asOneLead) rowId else totalRows + 1)
                                ),
                                color = color,
                                strokeWidth = strokeWidth,
                                cap = StrokeCap.Round
                            )
                        }

                        if (!input.showLinedNumbers and input.forBells.contains(bell)) {

                        } else {
                            if (input.showText) {
                                drawText(
                                    textMeasurer = textMeasurer,
                                    text = bell,
                                    topLeft = Offset(
                                        x = (spacingWidth * currentBellIndex) - (textSize.width / 2),
                                        y = (spacingHeight * (totalRows + 1)) - (textSize.height / 2)
                                    ),
                                    style = textStyle
                                )
                            }
                        }

                        previousBellIndex = currentBellIndex
                        totalRows++
                    }

                   if (input.showStartBells and !input.asOneLead) {
                       this.drawLeadSeparator(
                           stage = stage,
                           textSize = textSize,
                           totalRows = totalRows + 1,
                           spacingWidth = spacingWidth,
                           spacingHeight = spacingHeight
                       )
                   }
                }
            }

//            if (showText and (heightRatio == 5)) {
//                this.drawMethodText(
//                    method,
//                    forBells,
//                    showLinedNumbers,
//                    asOneLead,
//                    textMeasurer,
//                    textSize,
//                    textStyle,
//                    spacingWidth,
//                    spacingHeight
//                )
//            }

            if (input.showNotation) {
                this.drawNotation(
                    notation = input.methodProperty.notation ?: "",
                    textMeasurer = textMeasurer,
                    textStyle = textStyle,
                    spacingHeight = spacingHeight
                )
            }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }
}

private fun DrawScope.drawStartPositions(
    bell: String,
    lead: MutableList<String>,
    stage: Int,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    textSize: IntSize,
    fontSize: Int,
    totalRows: Int,
    spacingWidth: Float,
    spacingHeight: Float,
) {
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

    drawText(
        textMeasurer = textMeasurer,
        text = startBell,
        topLeft = Offset(
            x = (spacingWidth * (stage + 0.5f) - ((startBell.length - 1f) * textSize.width * 0.8f / 2)),
            y = spacingHeight * (totalRows - 1) - textSize.height * 0.8f / 2),
        style = textStyle.copy(fontSize = textStyle.fontSize * 0.8)
    )
}

private fun DrawScope.drawMethodText(
    method: Method,
    forBells: List<String>,
    showLinedNumbers: Boolean,
    asOneLead: Boolean,
    textMeasurer: TextMeasurer,
    textSize: IntSize,
    textStyle: TextStyle,
    spacingWidth: Float,
    spacingHeight: Float
) {
    var totalRows = 0

    // first row of rounds is not actually part of the method...
    for ((place, bell) in method[0][0].chunked(1).withIndex()) {
        if (!showLinedNumbers and forBells.contains(bell)) continue

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
                if (!showLinedNumbers and forBells.contains(bell)) continue

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

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Method Dark",
    widthDp = 400, heightDp = 1000)
@Composable
fun DisplayBlueLinePreview() {
//    val method = MethodPropertyEntity(notation = "5.1.5.1.5.1.5.1.5.1", stage = 5, title = "Plain hunt", id = "")
    val method = MethodPropertyEntity(notation = "5.1.5.1.5,125", stage = 5, title = "Plain bob doubles", id = "")
//    val method = MethodPropertyEntity(notation = "-36-14-12-36.14-12.56,12", stage = 6, title = "Surfleet minor", id = "")
//    val method = MethodPropertyEntity(notation = "-38-14-58-16-14-38-34-18,12", stage = 8, title = "Rutland Surprise Major", id = "")
//    val method = MethodPropertyEntity(notation = "3.1.7.3.1.3,1", stage = 7, title = "Stedman triples", id = "")
//    val method = MethodPropertyEntity(notation = "3,1.5.1.7.1.7.1", stage = 7, title = "Single oxford bob triples", id = "")
//    val method = MethodPropertyEntity(notatio = "3,1.5.1.7.3.7.5", stage = 7, title = "Double oxford bob triples", id = "")
//    val method = MethodPropertyEntity(notation = "-5D-14.5D-5D.36.14-7D.58.16-9D.70.18-ED.9T.10-AD.EB.1T-1T.AD-1T-1D,1D", stage = 16, title = "Bristol Surprise Sixteen", id = "")

    val input = BlueLineUiModel(
        modifier = Modifier
            .background(color = Color.White),
        methodProperty = method,
        forBells = listOf("1", "3"),
        treble = BlueLineStyle(color = Color.Red, strokeWidth = 2F),
        workingBells = BlueLineStyle(colors = listOf(Color.Blue), strokeWidth = 4F),
        asOneLead = false,
        fontSize = 10,
        showText = true,
        showVerticals = true,
        showStartBells = true,
        asMultiColumn = true,
        showLinedNumbers = false,
        showNotation = true,
        widthRatio = 4,
        heightRatio = 5,
    )

    MemringTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column() {
                Text(
                    text = method.title ?: "Unknown Method",
                    color = MaterialTheme.colorScheme.onBackground
                )
                DisplayBlueLine(
                    input
                )
            }
        }
    }
}