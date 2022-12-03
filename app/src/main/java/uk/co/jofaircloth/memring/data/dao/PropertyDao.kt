package uk.co.jofaircloth.memring.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import uk.co.jofaircloth.memring.data.entities.PropertyEntity

@Dao
interface PropertyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(property: PropertyEntity): Long
//
//    @Query("SELECT * from property WHERE id == :id")
//    fun getPropertyById(id: Int): Flow<PropertyEntity>
//
//    @Query("SELECT * FROM property WHERE stage == :stage")
//    fun getPropertiesByStage(stage: Int): Flow<List<PropertyEntity>>
}