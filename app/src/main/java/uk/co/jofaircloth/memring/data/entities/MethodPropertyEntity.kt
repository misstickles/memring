package uk.co.jofaircloth.memring.data.entities

import androidx.compose.runtime.Immutable
import androidx.room.*

@Immutable
data class MethodPropertyEntity (
    val id: String,
    val title: String? = "",
    val name: String? = "",
    val stage: Int = 0,
    val classification: String? = "",
    val isLittle: Boolean = false,
    val isPlain: Boolean = false,
    val isTrebleDodging: Boolean = false,
    val isDifferential: Boolean = false,
    val notation: String? = "",
    val lengthOfLead: Int? = 0,
    val huntbellPath: String? = "",
    val numberOfHunts: Int = 1,
    val leadHeadCode: String? = "",
    val leadHead: String? = "",
    val symmetry: String? = "",
    val fchGroup: String? = "",
    val notes: String? = "",
    val rwReference: String? = ""
)


//@Entity(
//    tableName = "methodProperty",
//    indices = [
//        Index("id", unique = true)
//    ]
//)
//@Immutable
//data class MethodPropertyEntity (
//    @PrimaryKey @ColumnInfo(name = "id") val id: String,
//    @ColumnInfo(name = "name") val name: String = "",
//    @ColumnInfo(name = "stage") val stage: Int = 0,
//    @ColumnInfo(name = "classification") val classification: String = "",
//    @ColumnInfo(name = "isLittle") val isLittle: Boolean = false,
//    @ColumnInfo(name = "isPlain") val isPlain: Boolean = false,
//    @ColumnInfo(name = "isTrebleDodging") val isTrebleDodging: Boolean = false,
//    @ColumnInfo(name = "isDifferential") val isDifferential: Boolean = false,
//    @ColumnInfo(name = "notation") val notation: String = "",
//    @ColumnInfo(name = "lengthOfLead") val lengthOfLead: Int = 0,
//    @ColumnInfo(name = "huntBellPath") val huntBellPath: String = "",
//    @ColumnInfo(name = "numberOfHunts") val numberOfHunts: Int = 1,
//    @ColumnInfo(name = "leadHeadCode") val leadHeadCode: String = "",
//    @ColumnInfo(name = "leadHead") val leadHead: String = "",
//    @ColumnInfo(name = "symmetry") val symmetry: String = "",
//    @ColumnInfo(name = "fchGroup") val fchGroup: String = "",
//    @ColumnInfo(name = "notes") val notes: String = "",
//    @ColumnInfo(name = "rwReference") val rwReference: String = ""
//)
