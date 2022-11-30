package uk.co.jofaircloth.memring.data.xml

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("falseness")
data class Falseness(
    @set:JsonProperty("falseCourseHeads") var falseCourseHeads: FalseCourseHeads?,
    @set:JsonProperty("fchGroups") var fchGroups: String?
)

@JsonRootName("falseCourseHeads")
data class FalseCourseHeads(
    @set:JsonProperty("inCourse") var inCourse: String?,
    @set:JsonProperty("outOfCourse") var outOfCourse: String?,
)