package uk.co.jofaircloth.memring.data.xml

//import com.fasterxml.jackson.databind.DeserializationFeature
//import com.fasterxml.jackson.databind.MapperFeature
//import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
//import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

//import com.fasterxml.jackson.module.kotlin.registerKotlinModule
//import uk.co.jofaircloth.memring.R

class MethodsCollectionRepository {

//    fun deserializeCollection(): Collection {
//        var mapper = jacksonObjectMapper()
//        return mapper.readValue<Collection>(CollectionData().data)
//        val module = JacksonXmlModule()
//        module.setDefaultUseWrapper(false);
//
//        val xmlMapper = XmlMapper(module)
//        xmlMapper.registerKotlinModule()
//        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//        xmlMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
//
//        return xmlMapper.readValue(CollectionData().data, Collection::class.java)
//    }

    private val xmlMapper = XmlMapper(JacksonXmlModule().apply {
        setDefaultUseWrapper(false)
    }).registerKotlinModule()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    private inline fun <reified T : Any> parseAs(): T {
//        var resource = this.javaClass.getResourceAsStream("methods.xml")?.bufferedReader()?.readText()
//        return xmlMapper.readValue(resource!!)
         return xmlMapper.readValue(CollectionData().data)
    }

    fun deserializeCollection(): Collection {
        return parseAs<Collection>()
    }
}