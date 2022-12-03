package uk.co.jofaircloth.memring.data.xml

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

@JsonRootName("properties")
data class Properties(
    @JsonProperty("stage") val stage: Int?,
    @JsonProperty("classification") val classification: Classification?,
    @JsonProperty("lengthOfLead") val lengthOfLead: Int?,
    @JsonProperty("numberOfHunts") val numberOfHunts: Int?,
    @JsonProperty("huntbellPath") val huntbellPath: String?,
    @JsonProperty("leadHeadCode") val leadHeadCode: String?,
    @JsonProperty("falseness") val falseness: Falseness?,
    @JsonProperty("symmetry") val symmetry: String?,
    @JsonProperty("extensionConstruction") val extensionConstruction: String?,
    @JsonProperty("meta") val meta: String?,
    @JsonProperty("notes") val notes: String?
)

@JsonRootName("classification")
class Classification {
    @JacksonXmlProperty(localName = "little", isAttribute = true)
    val isLittle: Boolean = false
    @JacksonXmlProperty(localName = "plain", isAttribute = true)
    val isPlain: Boolean = false
    @JacksonXmlProperty(localName = "differential", isAttribute = true)
    val isDifferential: Boolean = false
    @JacksonXmlProperty(localName = "trebleDodging", isAttribute = true)
    val isTrebleDodging: Boolean = false

    @JacksonXmlText
    val value: String? = null
}