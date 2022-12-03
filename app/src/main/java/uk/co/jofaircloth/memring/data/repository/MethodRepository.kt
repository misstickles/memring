package uk.co.jofaircloth.memring.data.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import uk.co.jofaircloth.memring.data.dao.MethodDao
import uk.co.jofaircloth.memring.data.dao.PropertyDao
import uk.co.jofaircloth.memring.data.entities.MethodEntity
import uk.co.jofaircloth.memring.data.entities.PropertyEntity

class MethodRepository(
    private val methodDao: MethodDao,
    private val propertyDao: PropertyDao
) {
    fun methodsByStage(stage: Int): Flow<List<MethodEntity>> = methodDao.methodsByStage(stage)

    fun methodsByName(name: String): Flow<List<MethodEntity>> = methodDao.methodsByName(name)

    @WorkerThread
    suspend fun insertProperty(property: PropertyEntity) {
        propertyDao.insert(property)
    }

    @WorkerThread
    suspend fun insertMethod(method: MethodEntity) {
        methodDao.insert(method)
    }
}