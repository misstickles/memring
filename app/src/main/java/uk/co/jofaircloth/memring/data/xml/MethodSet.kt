package uk.co.jofaircloth.memring.data.xml

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("methodSet")
data class MethodSet(
    @JsonProperty("notes") var notes: String?,
    @JsonProperty("properties") var properties: Properties?,
    @JsonProperty("method") var methods: List<Method>? = listOf()
)