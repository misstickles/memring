package uk.co.jofaircloth.memring.data.repository

import uk.co.jofaircloth.memring.data.dao.FavouriteDao
import uk.co.jofaircloth.memring.data.dao.MethodDao
import uk.co.jofaircloth.memring.data.dao.MethodPropertyDao

class CollectionRepository (
    private val methodDao: MethodDao,
    private val methodPropertyDao: MethodPropertyDao,
    private val favouriteDao: FavouriteDao
) {
    fun methodsByName(name: String, stage: Int, limit: Int = 20) = methodPropertyDao.methodsByName(name, stage, limit)

    fun methodsByStage(stage: Int) = methodDao.methodsByStage(stage)

//    suspend fun getFavourites(): Flow<List<FavouriteEntity>> = favouriteDao.allFavourites()

//    @WorkerThread
//    override suspend fun insertFavourite(favourite: FavouriteEntity) {
//        try {
//            favouriteDao.insert(favourite)
//        } catch (cause: Throwable) {
//            throw CollectionRepositoryError("Cannot update favourite preferences", cause)
//        }
//    }
}

class CollectionRepositoryError(message: String, cause: Throwable) : Throwable(message, cause)
