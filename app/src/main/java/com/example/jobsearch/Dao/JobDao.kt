package com.example.jobsearch.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jobsearch.entity.Job

@Dao
interface JobDao {
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: Job)

    @Update
    suspend fun updateJob(job: Job)

    @Query("SELECT * FROM jobs")
    suspend fun getAllJobs(): List<Job>

    @Query("DELETE FROM jobs WHERE id=:jobId")
    suspend fun deleteJob(jobId: Int)

    @Query("SELECT * FROM jobs WHERE id=:jobId LIMIT 1")
    suspend fun getJobById(jobId: Int): Job?
}
