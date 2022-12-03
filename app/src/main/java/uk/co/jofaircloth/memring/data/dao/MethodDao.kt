package uk.co.jofaircloth.memring.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uk.co.jofaircloth.memring.data.entities.MethodEntity

@Dao
interface MethodDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(method: MethodEntity)

    @Query("SELECT * FROM method WHERE name LIKE '%' || :name || '%'")
    fun methodsByName(name: String?): Flow<List<MethodEntity>>

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT method.*, property.* FROM method " +
            "INNER JOIN property ON method.propertyId = property.id " +
            "WHERE property.stage == :stage")
    fun methodsByStage(stage: Int): Flow<List<MethodEntity>>
}