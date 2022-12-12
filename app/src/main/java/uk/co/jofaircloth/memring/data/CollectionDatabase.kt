package uk.co.jofaircloth.memring.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uk.co.jofaircloth.memring.data.dao.*
import uk.co.jofaircloth.memring.data.entities.*

private const val TAG = "CollectionDatabase"

@Database(
    entities = [
        MethodEntity::class,
        PropertyEntity::class,
        PerformanceEntity::class,
        MethodPerformanceEntity::class,
        FavouriteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CollectionDatabase : RoomDatabase() {

    abstract fun methodDao(): MethodDao
    abstract fun propertyDao(): PropertyDao
    abstract fun methodPropertyDao(): MethodPropertyDao
    abstract fun performanceDao(): PerformanceDao
    abstract fun methodPerformanceDao(): MethodPerformanceDao
    abstract fun favouriteDao(): FavouriteDao

    private class CollectionDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d(TAG, "ONCREATE")
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