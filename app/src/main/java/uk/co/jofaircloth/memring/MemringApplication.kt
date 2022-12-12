package uk.co.jofaircloth.memring

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import uk.co.jofaircloth.memring.data.CollectionDatabase
import uk.co.jofaircloth.memring.data.repository.CollectionRepository

class MemringApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { CollectionDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { CollectionRepository(
        database.methodDao(),
        database.methodPropertyDao(),
        database.favouriteDao(),
    ) }
}