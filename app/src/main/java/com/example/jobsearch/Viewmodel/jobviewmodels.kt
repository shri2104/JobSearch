package com.example.jobsearch.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobsearch.entity.Job
import com.example.jobsearch.repo.JobRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(private val jobRepository: JobRepository) : ViewModel() {
    private val _jobs = MutableStateFlow<List<Job>>(emptyList())
    val jobs: StateFlow<List<Job>> = _jobs
    init {
        fetchJobs()
    }
    fun postJob(job: Job) {
        viewModelScope.launch {
            jobRepository.postJob(job)
              fetchJobs()
        }
    }
    fun updateJob(job: Job) {
        viewModelScope.launch {
            jobRepository.updateJob(job)
            fetchJobs()
        }
    }
    fun deleteJob(jobId: Int) {
        viewModelScope.launch {
            jobRepository.deleteJob(jobId)
            fetchJobs()
        }
    }

    private fun fetchJobs() {
        viewModelScope.launch {
            Log.e("APP Debugger", "Fetching Jobs")
            _jobs.value = jobRepository.getJobs()
        }
    }
    fun getJobById(jobId: Int): Job? {
        var job: Job? = null
        viewModelScope.launch {
            job = jobRepository.getJobById(jobId)
        }
        return job
    }

}
