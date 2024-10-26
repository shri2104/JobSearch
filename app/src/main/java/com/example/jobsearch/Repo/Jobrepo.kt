package com.example.jobsearch.repo

import com.example.jobsearch.dao.JobDao
import com.example.jobsearch.entity.Job
import javax.inject.Inject

class JobRepository @Inject constructor(private val jobDao: JobDao) {
    suspend fun postJob(job: Job) {
        jobDao.insertJob(job)
    }
    suspend fun updateJob(job: Job) {
        jobDao.updateJob(job)
    }
    suspend fun getJobs(): List<Job> {
        return jobDao.getAllJobs()
    }
    suspend fun deleteJob(jobId: Int) {
        jobDao.deleteJob(jobId)
    }
    suspend fun getJobById(jobId: Int): Job? {
        return jobDao.getJobById(jobId)
    }
}
