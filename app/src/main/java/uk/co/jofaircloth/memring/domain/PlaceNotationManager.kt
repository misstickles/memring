package uk.co.jofaircloth.memring.domain

import android.util.Log

class PlaceNotationManager {
    private val TAG = "PlaceNotationManager"

    private val bellNames: List<Char> = listOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'E', 'T', 'A', 'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L')

    /*
     * generate rows from 'startRow'
     */
    fun generateRows(notation: String, stage: Int): List<List<String>> {
        val rounds = rounds(stage)
        var currentRow: String = rounds

        val method = mutableListOf<MutableList<String>>()

        val changes = fullNotation(notation).split(".")

        // TODO maybe use given leadLength to ensure we are doing the right iterations...
        // TODO draw a line under lead length (repeat first row of next lead under it

        // for each lead, write out each change
        for (s in 1 .. stage) {
            val lead = mutableListOf<String>()
            lead.add(currentRow)

            // Number of leads in
            for (change in changes) {
                currentRow = when (change) {
                    "-" -> swapPairs(currentRow)
                    else -> swapPairsExcept(currentRow, change)
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

    /*
     * split on ',' if none, return as-is
     * split on '.' and between chars and -
     * split on ',' (lead end) then reverse if len > 1 (sometimes le is at start)
     *     append forward + reverse + le (or le + forward + reverse)
     * @Return formatted notation and every item separated by '.'
     */
    private fun fullNotation(notation: String): String {
        var separateWithDot = notation.replace("-", ".-.")
        separateWithDot = separateWithDot
            .replace("..", ".")
            .trim('.')

        val parts: List<String> = separateWithDot.split(",")

        Log.d(TAG, "Notation parts: $parts")

        if (parts.count() <= 1) {
            Log.d(TAG, "Only one part")
            return parts[0]
        }

        val fullNotation: MutableList<String> = emptyList<String>().toMutableList()

        for (p in parts) {
            val forward = p.trim('.').split(".")

            if (forward.count() == 1) {
                fullNotation += forward
                continue
            }

            val reverse = forward.reversed()

            fullNotation += (forward + reverse.slice(1 until reverse.count()))
        }

        Log.d(TAG, "Full notation: $fullNotation")
        return fullNotation.joinToString(separator = ".")
    }

    private fun swapPairs(currentRow: String): String {
        if (currentRow.isNullOrEmpty()) {
            return currentRow
        }

        val row = currentRow.toCharArray()

        for (place in 0 until row.count() - 1 step 2) {
            val tmp = row[place]
            row[place] = row[place + 1]
            row[place + 1] = tmp
        }

        return row.concatToString()
    }

    private fun swapPairsExcept(currentRow: String, except: String): String {
        if (currentRow.isNullOrEmpty()) {
            return currentRow
        }

        val row = currentRow.toCharArray()
        val doNotSwap = except.toCharArray()

        var place = 0
        while (place < row.count() - 1) {
            if (doNotSwap.contains(bellNames[place])) {
                place++
                continue
            }

            val tmp = row[place]
            row[place] = row[place + 1]
            row[place + 1] = tmp
            place += 2
        }

        return row.concatToString()
    }

    private fun rounds(stage: Int): String {
        return bellNames.slice(0 until stage).joinToString(separator = "")
    }
}