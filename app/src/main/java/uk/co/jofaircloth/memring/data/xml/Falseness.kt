package uk.co.jofaircloth.memring.data.xml

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("falseness")
data class Falseness(
    @JsonProperty("fchGroups") val fchGroups: String?
)