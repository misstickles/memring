package uk.co.jofaircloth.memring.domain

import android.util.Log
import uk.co.jofaircloth.memring.data.entities.MethodPropertyEntity
import uk.co.jofaircloth.memring.data.models.Method

class MethodManager {
    private val TAG = "MethodManager"

    fun generate(methodProperty: MethodPropertyEntity): Method {
        val stage = methodProperty.stage

        val rounds = PlaceNotationManager().rounds(stage)
        var currentRow: String = rounds

        val method = mutableListOf<MutableList<String>>()

        val changes = PlaceNotationManager().fullNotation(methodProperty.notation ?: "").split(".")

        // TODO maybe use given leadLength to ensure we are doing the right iterations...
        // TODO draw a line under lead length (repeat first row of next lead under it

        // for each lead, write out each change
        for (s in 0 until stage) {
            val lead = mutableListOf<String>()
            lead.add(currentRow)

            // Number of leads in
            for (change in changes) {
                currentRow = when (change) {
                    "-" -> PlaceNotationManager().swapPairs(currentRow)
                    else -> PlaceNotationManager().swapPairsExcept(currentRow, change)
                }
                lead.add(currentRow)
            }

            method.add(lead)

            if (currentRow == rounds) {
                break
            }
        }

        Log.d(TAG, "Generated Rows: $method")

        return method
    }

}