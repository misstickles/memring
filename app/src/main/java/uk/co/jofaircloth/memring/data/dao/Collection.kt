package uk.co.jofaircloth.memring.data.dao

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("collection")
data class Collection(
    @set:JsonProperty("collectionName") var name: String,
    @set:JsonProperty("notes") var notes: String,
    @set:JsonProperty("methodSet") var methodSet: List<MethodSet>
)