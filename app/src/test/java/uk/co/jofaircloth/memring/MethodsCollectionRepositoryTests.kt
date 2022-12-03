package uk.co.jofaircloth.memring

import org.junit.Assert
import org.junit.Test
import uk.co.jofaircloth.memring.data.xml.MethodsCollectionRepository

class DomainMethodsCollectionRepositoryTests {
    @Test
    fun deserializeXml() {
        val collection = MethodsCollectionRepository().deserializeCollection()

        Assert.assertEquals("Central Council Collection of Methods", collection.name)
        Assert.assertEquals(906, collection.methodSet.count())
        Assert.assertEquals(1, collection.methodSet[0].methods.count())
        Assert.assertEquals(76, collection.methodSet[93].methods.count())
        Assert.assertEquals("Cross Two", collection.methodSet[0].methods[0].title)
        Assert.assertEquals("21", collection.methodSet[0].methods[0].leadHead)
        Assert.assertEquals("m41230", collection.methodSet[0].methods[0].id)
//        Assert.assertEquals(true, collection.methodSet[899].properties.classification?.isLittle)
//        Assert.assertEquals("Hybrid", collection.methodSet[899].properties.classification?.text)
    }

    @Test
    fun deserializeCollectionXml() {
        val result = MethodsCollectionRepository().deserializeCollection()

        Assert.assertTrue(result.methodSet.count() > 5)
    }

}