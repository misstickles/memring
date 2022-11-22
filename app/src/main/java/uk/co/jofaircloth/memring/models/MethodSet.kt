package uk.co.jofaircloth.memring.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("methodSet")
data class MethodSet(
    @set:JsonProperty("notes") var notes: String,
    @set:JsonProperty("properties") var properties: Properties,
    @set:JsonProperty("method") var method: List<Method>
)