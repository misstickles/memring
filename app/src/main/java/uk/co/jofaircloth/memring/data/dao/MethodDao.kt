package uk.co.jofaircloth.memring.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uk.co.jofaircloth.memring.data.entities.MethodEntity

@Dao
abstract class MethodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(method: MethodEntity)

    @Query("SELECT * FROM method WHERE id = :id")
    abstract suspend fun methodById(id: String): MethodEntity?

    @Query(
        """
            SELECT * FROM method
            WHERE title LIKE '%' || :name || '%'
            ORDER BY title
            LIMIT :limit
        """)
    abstract fun methodsByName(
        name: String?,
        limit: Int = 20
    ): Flow<List<MethodEntity>>

    @Query(
        """
            SELECT method.*, property.*
            FROM method 
            INNER JOIN property ON method.propertyId = property.id
            WHERE property.stage == :stage
            LIMIT :limit
        """)
    abstract fun methodsByStage(stage: Int, limit: Int = 20): Flow<List<MethodEntity>>
}