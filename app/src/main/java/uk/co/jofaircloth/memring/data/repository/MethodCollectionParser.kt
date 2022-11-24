package uk.co.jofaircloth.memring.data.repository

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import uk.co.jofaircloth.memring.data.dao.MethodSet
import java.io.IOException
import java.io.InputStream

private val ns: String? = null

class MethodCollectionParser {
    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<*> {
        val parser: XmlPullParser = Xml.newPullParser()
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setInput(inputStream, null)
        parser.nextTag()
        return readFeed(parser)
    }

    private fun readFeed(parser: XmlPullParser): List<MethodSet> {
        val sets = mutableListOf<MethodSet>()
        parser.require(XmlPullParser.START_TAG, ns, "collection")

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == "methodSet") {
                sets.add(readMethodSet(parser))
            } else {
                skip(parser)
            }
        }

        return sets
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readMethodSet(parser: XmlPullParser): MethodSet {
        parser.require(XmlPullParser.START_TAG, ns, "methodSet")

        var properties: String
        var method: String
    }

    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw java.lang.IllegalStateException()
        }

        var depth = 1

        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}