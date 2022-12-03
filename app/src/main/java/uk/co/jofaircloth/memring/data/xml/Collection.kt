package uk.co.jofaircloth.memring.data.xml

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("collection")
data class Collection(
    @JsonProperty("collectionName") val name: String?,
    @JsonProperty("notes") val notes: String?,
    @JsonProperty("methodSet") val methodSet: List<MethodSet>? = listOf()
)