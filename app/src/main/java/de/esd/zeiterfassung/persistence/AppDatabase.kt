package de.esd.zeiterfassung.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CalendarConfigurationEntity::class, TitleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun calendarConfigurationDao(): CalendarConfigurationDao

    abstract fun titleDao(): TitleDao

    companion object {
        private const val DATABASE_NAME = "APP_DATABASE"
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDb(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            val instance = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
            INSTANCE = instance
            return instance
        }

    }
}