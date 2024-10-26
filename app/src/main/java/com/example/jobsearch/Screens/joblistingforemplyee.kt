package com.example.jobsearch.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jobsearch.entity.Job
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jobsearch.viewmodel.JobViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecruiterJobListScreen(
    viewModel: JobViewModel = hiltViewModel(),
    navController: NavController,
) {
    val jobList by viewModel.jobs.collectAsState(initial = emptyList())
    var filterText by remember { mutableStateOf("") }
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var jobToDelete by remember { mutableStateOf<Job?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Job Listings") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)) {
            TextField(
                value = filterText,
                onValueChange = { filterText = it },
                label = { Text("Filter by category or skill") },
                modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
            )

            val filteredJobs = jobList.filter { job ->
                filterText.isEmpty() ||
                        job.category.contains(filterText, ignoreCase = true) ||
                        job.skillsRequired.any { skill -> skill.contains(filterText, ignoreCase = true) }
            }

            Text(
                text = "${filteredJobs.size} job(s) available",
                modifier = Modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredJobs) { job ->
                    JobItem(
                        job = job,
                        isRecruiter = true,
                        onEditJob = {
                            navController.navigate("job_post_screen/${job.id}")
                        },
                        onDeleteJob = {
                            jobToDelete = job
                            showDeleteConfirmation = true
                        }
                    )
                }
            }
        }

        if (showDeleteConfirmation) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text("Delete Job") },
                text = { Text("Are you sure you want to delete this job listing?") },
                confirmButton = {
                    TextButton(onClick = {
                        jobToDelete?.let { job ->
                            viewModel.deleteJob(job.id)
                        }
                        showDeleteConfirmation = false
                        jobToDelete = null
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDeleteConfirmation = false
                        jobToDelete = null
                    }) {
                        Text("No")
                    }
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeJobListScreen(
    viewModel: JobViewModel = hiltViewModel(),
    navController: NavController
) {
    val jobList by viewModel.jobs.collectAsState(initial = emptyList())
    var filterText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Available Jobs") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)) {
            TextField(
                value = filterText,
                onValueChange = { filterText = it },
                label = { Text("Filter by category or skill") },
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
            )

            val filteredJobs = jobList.filter { job ->
                filterText.isEmpty() ||
                        job.category.contains(filterText, ignoreCase = true) ||
                        job.skillsRequired.any { skill -> skill.contains(filterText, ignoreCase = true) }
            }

            Text(
                text = "${filteredJobs.size} job(s) available",
                modifier = Modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredJobs) { job ->
                    JobItem(
                        job = job,
                        isRecruiter = false,
                        onEditJob = {},
                        onDeleteJob = {}
                    )
                }
            }
        }
    }
}


@Composable
fun JobItem(
    job: Job,
    isRecruiter: Boolean,
    onEditJob: () -> Unit,
    onDeleteJob: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Title: ${job.title}", style = MaterialTheme.typography.bodyLarge)
            Text("Organization: ${job.organizationName}")
            Text("Category: ${job.category}")
            Text("Skills Required: ${job.skillsRequired.joinToString(", ")}")
            Text("Status: ${job.status}")

            if (isRecruiter) {
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    Button(onClick = onEditJob, modifier = Modifier.padding(end = 8.dp)) {
                        Text("Edit")
                    }
                    Button(onClick = onDeleteJob, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}
