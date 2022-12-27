package uk.co.jofaircloth.memring.domain

import org.junit.Assert
import org.junit.Test

class PlaceNotationManagerShould {
    @Test
    fun BastowLittleBobMinimus() {
        val man = PlaceNotationManager().fullNotation("-12,14")

        Assert.assertEquals("-.12.-.14", man)
    }
}