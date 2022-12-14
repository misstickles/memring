package uk.co.jofaircloth.memring.data.entities

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "property",
    indices = [
        Index("id", unique = true),
    ]
)

@Immutable
data class PropertyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "stage") val stage: Int? = null,
    @ColumnInfo(name = "lengthOfLead") val lengthOfLead: Int? = null,
    @ColumnInfo(name = "numberOfHunts") val numberOfHunts: Int? = null,
    @ColumnInfo(name = "huntbellPath") val huntbellPath: String? = null,
    @ColumnInfo(name = "classification") val classification: String? = null, // Place | Bob | Slow Course | Treble Bob | Delight | Surprise | Alliance | Treble Place | Hybrid
    @ColumnInfo(name = "isDifferential") val isDifferential: Boolean = false,
    @ColumnInfo(name = "isLittle") val isLittle: Boolean = false,
    @ColumnInfo(name = "isPlain") val isPlain: Boolean = false,
    @ColumnInfo(name = "isTrebleDodging") val isTrebleDodging: Boolean = false
)