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
    val id: Int,
    @ColumnInfo(name = "towerDate") val towerDate: String,
    @ColumnInfo(name = "towerLocation") val towerLocation: String,
    @ColumnInfo(name = "towerSociety") val towerSociety: String,
    @ColumnInfo(name = "handDate") val handDate: String,
    @ColumnInfo(name = "handLocation") val handLocation: String,
    @ColumnInfo(name = "handSociety") val handSociety: String
)