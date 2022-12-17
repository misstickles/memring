package uk.co.jofaircloth.memring.domain

import android.util.Log
import uk.co.jofaircloth.memring.data.entities.MethodPropertyEntity
import uk.co.jofaircloth.memring.data.models.*

// https://complib.org/help#help-h-313
class BobSingleManager {
    private val TAG = "BobSingleManager"

    fun generate(methodProperty: MethodPropertyEntity, callNotation: String, callRows: Int = 8): Triple<MutableList<String>, String, String> {
        val foreAft = callRows / 2

        val method = MethodManager().generate(methodProperty)

        val firstLead = method[0]
        val finalRows = firstLead.slice(firstLead.count() - foreAft - 1 until firstLead.count())
        var currentRow = finalRows[0]

        var changes = PlaceNotationManager().fullNotation(methodProperty.notation ?: "").split(".")

        if (callNotation != "") {
            if (methodProperty.name == "Grandsire") {
                val c = callNotation.split(".")
                changes = changes.toMutableList().apply { this[changes.count() - 2] = c[0] }
                changes = changes.toMutableList().apply { this[changes.count() - 1] = c[1] }
            } else {
                changes = changes.toMutableList().apply { this[changes.count() - 1] = callNotation }
            }
        }

        // change rows = last n + first n
        changes = changes.slice(changes.count() - foreAft until changes.count()) +
                changes.slice(0 until foreAft - 1)

        val rows = mutableListOf<String>()
        rows.add(currentRow)

        // for each change of changes
        for (change in changes) {
            currentRow = when (change) {
                "-" -> PlaceNotationManager().swapPairs(currentRow)
                else -> PlaceNotationManager().swapPairsExcept(currentRow, change)
            }
            rows.add(currentRow)
        }

        Log.d(TAG, "Generated Rows: $rows")

        var affected: String = "1"
        if (method.count() > 1) {
            val originalRow = method[1][0]
            val calledRow = rows.elementAt(rows.count() - (callRows / 2))

            for (i in originalRow.indices) {
                val r1 = originalRow.elementAt(i)
                val r2 = calledRow.elementAt(i)

                if (r1 != r2) {
                    affected += r1
                }
            }
        }

        return Triple(rows, affected, changes.joinToString("."))
    }

    fun calls(method: MethodPropertyEntity, type: CallSymbol): String {
        val bobSingle = calls(method)

        return when (type) {
            CallSymbol.Single -> bobSingle.single ?: ""
            CallSymbol.Bob -> bobSingle.bob ?: ""
            CallSymbol.Omit -> bobSingle.omit ?: ""
            CallSymbol.Double -> bobSingle.double ?: ""
            CallSymbol.Extreme -> bobSingle.extreme ?: ""
            CallSymbol.BigBob -> bobSingle.bigBob ?: ""
            CallSymbol.BobSingle -> bobSingle.bobSingle ?: ""
        }
    }

    private fun calls(method: MethodPropertyEntity): BobSingle {
        val evenStage = method.stage % 2 == 0

        if (method.name?.lowercase() == "grandsire") {
            val suffix = if (evenStage) BellNames[method.stage - 1] else ""
            return BobSingle(
                bob = "3$suffix.1$suffix",
                single = "3$suffix.123$suffix"
            )
        }
        else if (method.name?.lowercase() == "stedman") {
            val start = method.stage - 2 - 1
            return BobSingle(
                bob = BellNames[start].toString(),
                single = "${BellNames[start]}${BellNames[start + 1]}${BellNames[start + 2]}"
            )
        }
        else {
            val split = method.notation!!.split(",")

            if (split.count() > 1) {
                if (method.notation.split(",")[1].startsWith("12")) { // near methods
                    val suffix = if (evenStage) "" else BellNames[method.stage - 1]
                    val isDoubles = method.stage == 5

                    return BobSingle(
                        bob = "14$suffix",
                        single = if (isDoubles) "123" else "1234$suffix",
                        double = if (!isDoubles) "123456$suffix" else null,
                        bobSingle = if (!isDoubles) "1456$suffix" else null,
                        bigBob = if (!isDoubles) "16$suffix" else null,
                        extreme = if (isDoubles) "125" else null,
                        omit = if (isDoubles) "1" else null
                    )
                } else { // far methods
                    val start = method.stage - 2 - 1
                    val prefix = if (evenStage) "1" else ""
                    val suffix = BellNames[start]

                    val isMinorOrUp = method.stage >= 6

                    return BobSingle(
                        bob = "$prefix$suffix",
                        single = "$prefix${BellNames[start]}${BellNames[start + 1]}${BellNames[start + 2]}",
                        double = if (isMinorOrUp) "$prefix${BellNames[start - 2]}${BellNames[start - 1]}${BellNames[start]}${BellNames[start + 1]}${BellNames[start + 2]}" else null,
                        bobSingle = if (isMinorOrUp) "$prefix${BellNames[start - 2]}${BellNames[start - 1]}${BellNames[start]}" else null,
                        bigBob = if (isMinorOrUp) "$prefix${BellNames[start - 2]}" else null
                    )
                }
            } else {
                return BobSingle(
                    bob = "",
                    single = ""
                )
            }
        }
    }
}