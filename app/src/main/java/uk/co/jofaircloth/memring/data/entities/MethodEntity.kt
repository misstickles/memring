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
        ForeignKey(
            entity = PropertyEntity::class,
            parentColumns = ["id"],
            childColumns = ["propertyId"]
        )
    ],
    indices = [
        Index("id", unique = true),
        Index("id", "propertyId", unique = true)
    ]
)

@Immutable
data class MethodEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "propertyId") val propertyId: Int,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "notation") val notation: String? = null,
    @ColumnInfo(name = "leadHead") val leadHead: String? = null,
    @ColumnInfo(name = "leadHeadCode") val leadHeadCode: String? = null,
    @ColumnInfo(name = "fchGroups") val fchGroups: String? = null, // TODO this is an object
    @ColumnInfo(name = "symmetry") val symmetry: String? = null, // palindromic | double | rotational
    @ColumnInfo(name = "extensionConstruction") val extensionConstruction: String? = null,
    @ColumnInfo(name = "notes") val notes: String? = null,
    @ColumnInfo(name = "rwReference") val rwReference: String? = null, // TODO this is an object
)