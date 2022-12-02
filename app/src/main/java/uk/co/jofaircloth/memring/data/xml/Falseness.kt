package uk.co.jofaircloth.memring.data.xml

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("falseness")
data class Falseness(
    @JsonProperty("falseCourseHeads") var falseCourseHeads: FalseCourseHeads?,
    @JsonProperty("fchGroups") var fchGroups: String?
)

@JsonRootName("falseCourseHeads")
data class FalseCourseHeads(
    @JsonProperty("inCourse") var inCourse: String?,
    @JsonProperty("outOfCourse") var outOfCourse: String?,
)