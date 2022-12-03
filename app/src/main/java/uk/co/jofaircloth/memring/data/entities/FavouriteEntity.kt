package uk.co.jofaircloth.memring.data.entities

import androidx.compose.runtime.Immutable
import androidx.room.*

@Entity(
    tableName = "favourite",
    foreignKeys = [
        ForeignKey(
            entity = MethodEntity::class,
            parentColumns = ["id"],
            childColumns = ["methodId"]
        ),
    ],
    indices = [
        Index("methodId", unique = true)
    ]
)
@Immutable
data class FavouriteEntity (
    @PrimaryKey
    @ColumnInfo(name = "methodId")
    val methodId: String,
    @ColumnInfo(name = "dateAdded") val dateAdded: String
)