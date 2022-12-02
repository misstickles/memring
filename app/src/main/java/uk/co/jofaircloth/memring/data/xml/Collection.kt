package uk.co.jofaircloth.memring.data.xml

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("collection")
data class Collection(
    @JsonProperty("collectionName") var name: String?,
    @JsonProperty("notes") var notes: String?,
    @JsonProperty("methodSet") var methodSet: List<MethodSet>? = listOf()
)