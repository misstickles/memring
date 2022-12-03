package uk.co.jofaircloth.memring.data.xml

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

@JsonRootName("method")
data class Method (
    @JacksonXmlProperty(isAttribute = true) val id: String,
    @JsonProperty("title") val title: String = "",
    @JsonProperty("name") val name: String?, // nullable reqd for when <name /> @30541
    @JsonProperty("notation") val notation: String?,
    @JsonProperty("symmetry") val symmetry: String?,
    @JsonProperty("leadHeadCode") val leadHeadCode: String?,
    @JsonProperty("leadHead") val leadHead: String?,
    @JsonProperty("notes") val notes: String?,
    @JsonProperty("extensionConstruction") val extensionConstruction: String?,
    @JsonProperty("falseness") val falsness: Falseness?,
    @JsonProperty("performances") val performances: Performance?,
    @JsonProperty("references") private val reference: Reference?,
    val rwReference: String? = reference?.rwRef
)

data class Reference(
    @JsonProperty("rwRef") val rwRef: String?
)
