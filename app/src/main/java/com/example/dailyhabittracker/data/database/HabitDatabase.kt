package com.example.dailyhabittracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dailyhabittracker.data.models.Habit
import com.example.dailyhabittracker.logic.dao.HabitDao

@Database(entities = [Habit::class], version = 2, exportSchema = false)
abstract class HabitDatabase: RoomDatabase() {
    abstract fun habitDao() : HabitDao
    companion object{
        @Volatile
        private var INSTANCE: HabitDatabase? = null
        fun getDatabase(context: Context): HabitDatabase {
            val tempInstance : HabitDatabase? = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(lock = this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    name = "habit_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}