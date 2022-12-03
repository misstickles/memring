package uk.co.jofaircloth.memring.data.xml

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("methodSet")
data class MethodSet(
    @JsonProperty("notes") val notes: String?,
    @JsonProperty("properties") val properties: Properties?,
    @JsonProperty("method") val methods: List<Method>? = listOf()
)