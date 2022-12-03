package uk.co.jofaircloth.memring.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uk.co.jofaircloth.memring.data.dao.MethodDao
import uk.co.jofaircloth.memring.data.dao.MethodPerformanceDao
import uk.co.jofaircloth.memring.data.dao.PerformanceDao
import uk.co.jofaircloth.memring.data.dao.PropertyDao
import uk.co.jofaircloth.memring.data.entities.*

private const val TAG = "CollectionDatabase"

@Database(
    entities = [
        MethodEntity::class,
        PerformanceEntity::class,
        PropertyEntity::class,
        MethodPerformanceEntity::class,
        FavouriteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CollectionDatabase : RoomDatabase() {

    abstract fun methodDao(): MethodDao
    abstract fun performanceDao(): PerformanceDao
    abstract fun propertyDao(): PropertyDao
    abstract fun methodPerformanceDao(): MethodPerformanceDao

    private class CollectionDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d(TAG, "ONCREEATE")
            INSTANCE?.let { database ->
                scope.launch {
                    populateCollectionDatabase(
                        database.methodDao(),
                        database.performanceDao(),
                        database.propertyDao(),
                        database.methodPerformanceDao()
                    )
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: CollectionDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): CollectionDatabase {

            Log.d(TAG, "GETDATABASE")

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CollectionDatabase::class.java,
                    "CollectionDb"
                )
                    .addCallback(CollectionDatabaseCallback(scope))
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}