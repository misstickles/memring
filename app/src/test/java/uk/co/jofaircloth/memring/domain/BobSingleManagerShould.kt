package uk.co.jofaircloth.memring.domain
//
//import org.junit.Assert
//import org.junit.Test
//import uk.co.jofaircloth.memring.data.entities.MethodPropertyEntity
//import uk.co.jofaircloth.memring.data.models.BobSingle
//
//// https://complib.org/help#help-h-313
//class BobSingleManagerShould {
//    @Test
//    fun createBobSingleForGrandsire() {
//        val methodProperty = MethodPropertyEntity(
//            id = "m1234",
//            title = "Grandsire Doubles",
//            name = "Grandsire",
//            stage = 5,
//            notation = "3,1.5.1.5.1"
//        )
//
//        val grandsireBobSingle = BobSingle(
//            bob =  "3.1",
//            single = "3.123"
//        )
//
//        Assert.assertEquals(grandsireBobSingle, BobSingleManager().generate(methodProperty))
//    }
//
//    @Test
//    fun createBobSingleForGrandsireEven() {
//        val methodProperty = MethodPropertyEntity(
//            id = "m1234",
//            title = "Grandsire Maximus",
//            name = "Grandsire",
//            stage = 12,
//            notation = "3T,1T-1T-1T-1T-1T-1T-"
//        )
//
//        val grandsireBobSingle = BobSingle(
//            bob =  "3T.1T",
//            single = "3T.123T"
//        )
//
//        Assert.assertEquals(grandsireBobSingle, BobSingleManager().generate(methodProperty))
//    }
//
//    @Test
//    fun createBobSingleForStedman() {
//        val methodProperty = MethodPropertyEntity(
//            id = "m1234",
//            title = "Stedman Sextuples",
//            name = "Stedman",
//            stage = 13,
//            notation = "3.1.A.3.1.3,1"
//        )
//
//        val stedmanBobSingle = BobSingle(
//            bob =  "E",
//            single = "ETA"
//        )
//
//        Assert.assertEquals(stedmanBobSingle, BobSingleManager().generate(methodProperty))
//    }
//
//    @Test
//    fun createBobSingleForNearEven() {
//        val methodProperty = MethodPropertyEntity(
//            id = "m1234",
//            title = "Cambridge Surprise Royal",
//            name = "Cambridge",
//            stage = 10,
//            notation = "-30-14-1250-36-1470-58-16-70-18-90,12"
//        )
//
//        val cambridgeBobSingle = BobSingle(
//            bob =  "14",
//            single = "1234",
//            double = "123456",
//            bobSingle = "1456",
//            bigBob = "16"
//        )
//
//        Assert.assertEquals(cambridgeBobSingle, BobSingleManager().generate(methodProperty))
//    }
//
//    @Test
//    fun createBobSingleForNearOdd() {
//        val methodProperty = MethodPropertyEntity(
//            id = "m1234",
//            title = "Snoopy Bob Triples",
//            name = "Snoopy",
//            stage = 7,
//            notation = "34567.1.5.123.7.12345.7,127"
//        )
//
//        val snoopyBobSingle = BobSingle(
//            bob =  "147",
//            single = "12347",
//            double = "1234567",
//            bobSingle = "14567",
//            bigBob = "167"
//        )
//
//        Assert.assertEquals(snoopyBobSingle, BobSingleManager().generate(methodProperty))
//    }
//
//    @Test
//    fun createBobSingleForFarEven() {
//        val methodProperty = MethodPropertyEntity(
//            id = "m1234",
//            title = "Norwich Surprise Minor",
//            name = "Norwich",
//            stage = 6,
//            notation = "-34-14-12-36-34-16,16"
//        )
//
//        val norwichBobSingle = BobSingle(
//            bob =  "14",
//            single = "1456",
//            double = "123456",
//            bobSingle = "1234",
//            bigBob = "12"
//        )
//
//        Assert.assertEquals(norwichBobSingle, BobSingleManager().generate(methodProperty))
//    }
//
//    @Test
//    fun createBobSingleForFarOdd() {
//        val methodProperty = MethodPropertyEntity(
//            id = "m1234",
//            title = "Reverse Bob Triples",
//            name = "Reverse",
//            stage = 7,
//            notation = "3.1.5.3.7.5.167,1"
//        )
//
//        val reverseBobSingle = BobSingle(
//            bob =  "5",
//            single = "567",
//            double = "34567",
//            bobSingle = "345",
//            bigBob = "3"
//        )
//
//        Assert.assertEquals(reverseBobSingle, BobSingleManager().generate(methodProperty))
//    }
//}