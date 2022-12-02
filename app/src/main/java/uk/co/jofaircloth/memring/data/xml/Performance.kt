package uk.co.jofaircloth.memring.data.xml

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

data class Performance(
    @JsonProperty("firstTowerbellPeal") val firstTowerbellPeal: FirstPeal?,
    @JsonProperty("firstHandbellPeal") val firstHandbellPeal: FirstPeal?
)

data class FirstPeal(
    @JsonProperty("date") val date: String?,
    @JsonProperty("location") val location: Location?,
    @JsonProperty("society") val society: String?
)

@JsonRootName("location")
data class Location(
    @JsonProperty("building") val building: String?,
    @JsonProperty("town") val town: String?,
    @JsonProperty("county") val county: String?
)