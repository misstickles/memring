package uk.co.jofaircloth.memring.data.dao

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

@JsonRootName("method")
data class Method (
    @set:JacksonXmlProperty(isAttribute = true) var id: String,
    @set:JsonProperty("title") var title: String?,
    @set:JsonProperty("name") var name: String?,
    @set:JsonProperty("notation") var notation: String?,
    @set:JsonProperty("symmetry") var symmetry: String?,
    @set:JsonProperty("leadHeadCode") var leadHeadCode: String?,
    @set:JsonProperty("leadHead") var leadHead: String?,
    @set:JsonProperty("notes") var notes: String?
)
