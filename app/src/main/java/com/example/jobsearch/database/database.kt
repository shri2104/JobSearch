package com.example.jobsearch.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jobsearch.dao.JobDao
import com.example.jobsearch.entity.Job
import android.content.Context
import androidx.room.Room
import androidx.room.TypeConverters
import com.example.jobsearch.Dao.UserDao
import com.example.jobsearch.Entity.User
import com.example.jobsearch.entity.Converters


@Database(entities = [Job::class, User::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context:Context):AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "jobsearch_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
