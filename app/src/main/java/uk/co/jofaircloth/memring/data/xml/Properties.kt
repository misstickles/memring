package uk.co.jofaircloth.memring.data.xml

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText
import uk.co.jofaircloth.memring.data.xml.Falseness

@JsonRootName("properties")
data class Properties(
    @JsonProperty("stage") var stage: Int?,
    @JsonProperty("classification") var classification: Classification?,
    @JsonProperty("lengthOfLead") var lengthOfLead: Int?,
    @JsonProperty("numberOfHunts") var numberOfHunts: Int?,
    @JsonProperty("huntbellPath") var huntbellPath: String?,
    @JsonProperty("leadHeadCode") var leadHeadCode: String?,
    @JsonProperty("falseness") var falsness: Falseness?,
    @JsonProperty("symmetry") var symmetry: String?,
    @JsonProperty("extensionConstruction") var extensionConstruction: String?,
    @JsonProperty("meta") var meta: String?,
    @JsonProperty("notes") var notes: String?
)

@JsonRootName("classification")
class Classification {
    @JacksonXmlProperty(localName = "little", isAttribute = true)
    var isLittle: Boolean = false
    @JacksonXmlProperty(localName = "plain", isAttribute = true)
    var isPlain: Boolean = false
    @JacksonXmlProperty(localName = "differential", isAttribute = true)
    var isDifferential: Boolean = false
    @JacksonXmlProperty(localName = "trebleDodging", isAttribute = true)
    var isTrebleDodging: Boolean = false

    @JacksonXmlText
    var value: String? = null
}