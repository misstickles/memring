package uk.co.jofaircloth.memring.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import uk.co.jofaircloth.memring.data.entities.PerformanceEntity

@Dao
interface PerformanceDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(performance: PerformanceEntity)
}