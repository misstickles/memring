package uk.co.jofaircloth.memring.data.xml

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

@JsonRootName("method")
data class Method (
    @JacksonXmlProperty(isAttribute = true) var id: String,
    @JsonProperty("title") var title: String = "",
    @JsonProperty("name") var name: String? = "", // nullable reqd for when <name /> @30541
    @JsonProperty("notation") var notation: String = "",
    @JsonProperty("symmetry") var symmetry: String = "",
    @JsonProperty("leadHeadCode") var leadHeadCode: String = "",
    @JsonProperty("leadHead") var leadHead: String = "",
    @JsonProperty("notes") var notes: String = "",
    @JsonProperty("meta") var meta: String = "",
    @JsonProperty("extensionConstruction") val extensionConstruction: String = ""
)
