package uk.co.jofaircloth.memring.data.xml

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import uk.co.jofaircloth.memring.data.xml.Falseness

@JsonRootName("properties")
data class Properties(
//    @set:JsonProperty("classification") var classification: Classification?, // TODO classification
    @set:JsonProperty("stage") var stage: Int?,
    @set:JsonProperty("lengthOfLead") var lengthOfLead: Int?,
    @set:JsonProperty("numberOfHunts") var numberOfHunts: Int?,
    @set:JsonProperty("huntbellPath") var huntbellPath: String?,
    @set:JsonProperty("leadHead") var leadHead: String?,
    @set:JsonProperty("leadHeadCode") var leadHeadCode: String?,
    @set:JsonProperty("falseness") var falsness: Falseness?,
    @set:JsonProperty("symmetry") var symmetry: String?,
    @set:JsonProperty("extensionConstruction") var extensionConstruction: String?,
    @set:JsonProperty("meta") var meta: String?,
    @set:JsonProperty("notes") var notes: String?
)

@JsonRootName("classification")
data class Classification(
    @JacksonXmlProperty(localName = "little", isAttribute = true) val isLittle: Boolean = false,
    @JacksonXmlProperty(localName = "plain", isAttribute = true) val isPlain: Boolean = false,
    @JacksonXmlProperty(localName = "differential", isAttribute = true) val isDifferential: Boolean = false,
    @JacksonXmlProperty(localName = "trebleDodging", isAttribute = true) val isTrebleDodging: Boolean = false,

    @JacksonXmlProperty(localName = "innerText") var text: String?
)