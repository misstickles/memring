package uk.co.jofaircloth.memring.data.xml

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import uk.co.jofaircloth.memring.data.xml.Falseness

@JsonRootName("properties")
data class Properties(
//    @JsonProperty("classification") var classification: Classification?, // TODO classification
    @JsonProperty("stage") var stage: Int?,
    @JsonProperty("lengthOfLead") var lengthOfLead: Int?,
    @JsonProperty("numberOfHunts") var numberOfHunts: Int?,
    @JsonProperty("huntbellPath") var huntbellPath: String?,
    @JsonProperty("leadHead") var leadHead: String?,
    @JsonProperty("leadHeadCode") var leadHeadCode: String?,
    @JsonProperty("falseness") var falsness: Falseness?,
    @JsonProperty("symmetry") var symmetry: String?,
    @JsonProperty("extensionConstruction") var extensionConstruction: String?,
    @JsonProperty("meta") var meta: String?,
    @JsonProperty("notes") var notes: String?
)

@JsonRootName("classification")
data class Classification(
    @JacksonXmlProperty(localName = "little", isAttribute = true) val isLittle: Boolean = false,
    @JacksonXmlProperty(localName = "plain", isAttribute = true) val isPlain: Boolean = false,
    @JacksonXmlProperty(localName = "differential", isAttribute = true) val isDifferential: Boolean = false,
    @JacksonXmlProperty(localName = "trebleDodging", isAttribute = true) val isTrebleDodging: Boolean = false,

    @JacksonXmlProperty(localName = "innerText") var text: String?
)