package uk.co.jofaircloth.memring

import org.junit.Test

import org.junit.Assert.*
import uk.co.jofaircloth.memring.data.MethodsCollectionRepository

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun deserializeCollectionXml() {
        val result = MethodsCollectionRepository.deserializeMethodCollection()

        assertTrue(result.methodSet.count() > 5)
    }
}