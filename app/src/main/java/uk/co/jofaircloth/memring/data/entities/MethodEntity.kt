package uk.co.jofaircloth.memring.data.entities

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "method",
    foreignKeys = [
        ForeignKey(
            entity = PropertyEntity::class,
            parentColumns = ["id"],
            childColumns = ["propertyId"]
        ),
//        ForeignKey(
//            entity = PerformanceEntity::class,
//            parentColumns = ["id"],
//            childColumns = ["performanceId"]
//        )
    ],
    indices = [
        Index("id", "propertyId", unique = true)
    ]
)

// TODO method also has a decision year attr
// TODO a lot of method in the docs is duplicate of properties...
@Immutable
data class MethodEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "propertyId") val propertyId: Int,
    @ColumnInfo(name = "performanceId") val performanceId: Int,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "notation") val notation: String? = null,
    @ColumnInfo(name = "leadHead") val leadHead: String? = null,
    @ColumnInfo(name = "leadHeadCode") val leadHeadCode: String? = null,
    @ColumnInfo(name = "falseness") val falseness: String? = null, // TODO this is an object
    @ColumnInfo(name = "symmetry") val symmetry: String? = null, // palindromic | double | rotational
    @ColumnInfo(name = "extensionConstruction") val extensionConstruction: String? = null,
    @ColumnInfo(name = "notes") val notes: String? = null,
    @ColumnInfo(name = "meta") val meta: String? = null,
    @ColumnInfo(name = "rwReference") val rwReference: String? = null, // TODO this is an object
)