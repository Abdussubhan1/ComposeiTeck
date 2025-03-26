import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.itecktestingcompose.DataBase.DeviceSearchHistory
import com.example.itecktestingcompose.DataBase.DeviceSearchHistoryDAO

// Define the database with entities and version
@Database(entities = [DeviceSearchHistory::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Provide DAO instance
    abstract fun getHistory(): DeviceSearchHistoryDAO


    //this pattern is a Singleton

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "device_search_history_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
