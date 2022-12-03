package uk.co.jofaircloth.memring.data.entities

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "methodPerformance",
    indices = [
        Index("methodId", "performanceId", unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = MethodEntity::class,
            parentColumns = ["id"],
            childColumns = ["methodId"]
        ),
        ForeignKey(
            entity = PerformanceEntity::class,
            parentColumns = ["id"],
            childColumns = ["performanceId"]
        )
    ]
)

@Immutable
data class MethodPerformanceEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "methodId") val methodId: String,
    @ColumnInfo(name = "performanceId") val performanceId: Int,
)