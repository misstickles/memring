package uk.co.jofaircloth.memring.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import uk.co.jofaircloth.memring.data.entities.MethodPerformanceEntity

@Dao
interface MethodPerformanceDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(methodPerformance: MethodPerformanceEntity): Long
}