package uk.co.jofaircloth.memring.data.entities

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "performance",
    indices = [
        Index("id", unique = true)
    ]
)

// TODO method also has a decision year attr
// TODO a lot of method in the docs is duplicate of properties...
@Immutable
data class PerformanceEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "building") val building: String?,
    @ColumnInfo(name = "town") val town: String?,
    @ColumnInfo(name = "county") val county: String?,
    @ColumnInfo(name = "society") val society: String?,
    @ColumnInfo(name = "type") val type: String? // HAND | TOWER
)