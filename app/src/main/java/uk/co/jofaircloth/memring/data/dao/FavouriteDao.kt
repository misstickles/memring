package uk.co.jofaircloth.memring.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uk.co.jofaircloth.memring.data.entities.FavouriteEntity

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(favourite: FavouriteEntity)

    @Query("SELECT * FROM favourite")
    fun allFavourites(): Flow<List<FavouriteEntity>>
//    @Query("SELECT favourite.*" +
////            "method.id, method.title, property.stage " +
//            "FROM method " +
//            "INNER JOIN property ON property.id = method.propertyId " +
//            "INNER JOIN favourite ON method.id = favourite.methodId " +
//            "ORDER BY favourite.dateAdded DESC")
//    fun allFavourites(): Flow<List<FavouriteEntity>>
}