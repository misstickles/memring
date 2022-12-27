package uk.co.jofaircloth.memring.domain

import android.util.Log
import uk.co.jofaircloth.memring.data.models.BellNames

class PlaceNotationManager {
    private val TAG = "PlaceNotationManager"

    /*
     * split on ',' if none, return as-is
     * split on '.' and between chars and -
     * split on ',' (lead end) then reverse if len > 1 (sometimes le is at start)
     *     append forward + reverse + le (or le + forward + reverse)
     * @Return formatted notation and every item separated by '.'
     */
    fun fullNotation(notation: String): String {
        var separateWithDot = notation.replace("-", ".-.")
        separateWithDot = separateWithDot
            .replace("..", ".")
            .trim('.')

        val parts: List<String> = separateWithDot.split(",")

        if (parts.count() <= 1) {
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

    fun swapPairs(currentRow: String): String {
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

    fun swapPairsExcept(currentRow: String, except: String): String {
        if (currentRow.isNullOrEmpty()) {
            return currentRow
        }

        val row = currentRow.toCharArray()
        val doNotSwap = except.toCharArray()

        var place = 0
        while (place < row.count() - 1) {
            if (doNotSwap.contains(BellNames[place])) {
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

    fun rounds(stage: Int): String {
        return BellNames.slice(0 until stage).joinToString(separator = "")
    }
}