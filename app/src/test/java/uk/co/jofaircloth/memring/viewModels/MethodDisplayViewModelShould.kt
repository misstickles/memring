package uk.co.jofaircloth.memring.viewModels

import org.junit.Assert
import org.junit.Test

class MethodDisplayViewModelShould {
    //    @Test
//    fun getMethodsForStage() {
//        val data = MethodDisplayViewModel(
//            collectionDatabase = CollectionDatabase.getInstance()
//        ).selectMethodsForStage(22)
//
//        Assert.assertEquals(1, data.count())
//    }
    @Test
    fun extractDigitAndStringFromSearch() {
        val search = "London 6"

        val letters = search.filter { it.isLetter() }
        val digits = search.filter { it.isDigit() }

        Assert.assertEquals("London", letters)
        Assert.assertEquals("6", digits)
    }

    @Test
    fun useEmptyIfNoDigit() {
        val search = "London"

        val letters = search.filter { it.isLetter() }
        val digits = search.filter { it.isDigit() }

        Assert.assertEquals("London", letters)
        Assert.assertEquals("", digits)
    }
}