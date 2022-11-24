package uk.co.jofaircloth.memring.data.repository

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import uk.co.jofaircloth.memring.data.MethodsXml.MethodsXml
import uk.co.jofaircloth.memring.data.dao.Collection

object MethodsCollectionRepository {
    private val xmlMapper = XmlMapper(JacksonXmlModule()
        .apply {
            setDefaultUseWrapper(false)
            setXMLTextElementName("innerText")
        })
        .registerKotlinModule()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    private inline fun <reified T : Any> parseAs(path: String): T {
//        val resource = ClassLoader::class.java.classLoader?.getResourceAsStream("methods.xml")
//        var resource: String = """methods.xml"""
//        return xmlMapper.readValue(resource!!)
//        val xmlFactory = XmlFactory
//            .builder()
//            .xmlInputFactory(WstxInputFactory())
//            .xmlOutputFactory(WstxOutputFactory())
//            .build()
//
        return xmlMapper.readValue(MethodsXml.methodsXml)
    }

    fun deserializeMethodCollection(): Collection {
        return parseAs<Collection>("")
    }
}