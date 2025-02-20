package com.example.jobsearch.database

import android.content.Context
import androidx.room.Room
import com.example.jobsearch.Dao.UserDao
import com.example.jobsearch.Repo.UserRepository
import com.example.jobsearch.dao.JobDao
import com.example.jobsearch.repo.JobRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "jobsearch_database").build()
    }

    @Provides
    @Singleton
    fun provideJobDao(database:AppDatabase): JobDao {
        return database.jobDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(database:AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideJobRepository(jobDao:JobDao): JobRepository {
        return JobRepository(jobDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }
}
