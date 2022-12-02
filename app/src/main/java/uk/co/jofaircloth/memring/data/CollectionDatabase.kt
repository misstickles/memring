// https://methods.cccbr.org.uk/notes.html
// https://methods.cccbr.org.uk/method_xml_1.0.pdf
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
import uk.co.jofaircloth.memring.data.dao.PerformanceDao
import uk.co.jofaircloth.memring.data.dao.PropertyDao
import uk.co.jofaircloth.memring.data.entities.MethodEntity
import uk.co.jofaircloth.memring.data.entities.PerformanceEntity
import uk.co.jofaircloth.memring.data.entities.PropertyEntity

private const val TAG = "CollectionDatabase"

@Database(
    entities = [
        MethodEntity::class,
        PerformanceEntity::class,
        PropertyEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class CollectionDatabase : RoomDatabase() {

    abstract fun methodDao(): MethodDao
    abstract fun performanceDao(): PerformanceDao
    abstract fun propertyDao(): PropertyDao

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
                        database.propertyDao()
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