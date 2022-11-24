package uk.co.jofaircloth.memring.viewModels

import org.junit.Assert
import org.junit.Test
import uk.co.jofaircloth.memring.ui.methodDisplay.MethodDisplayViewModel

class MethodDisplayViewModelShould {
    @Test
    fun getMethodsForStage() {
        val data = MethodDisplayViewModel().selectMethodsForStage(22)

        Assert.assertEquals(1, data.count())
    }
}