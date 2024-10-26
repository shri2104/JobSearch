package com.example.jobsearch.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jobsearch.entity.Job
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jobsearch.viewmodel.JobViewModel
import kotlinx.coroutines.launch

@Composable
fun JobPostScreen(
    viewModel: JobViewModel = hiltViewModel(),
    onJobPosted: () -> Unit,
    jobId: String? = null,
    navController: NavHostController
) {
    var title by remember { mutableStateOf("") }
    var organizationName by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var skillsRequired by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Applied") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(jobId) {
        jobId?.let {
            val jobDetails = viewModel.getJobById(it.toInt())
            if (jobDetails != null) {
                title = jobDetails.title
                organizationName = jobDetails.organizationName
                category = jobDetails.category
                skillsRequired = jobDetails.skillsRequired.joinToString(", ")
                status = jobDetails.status
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center)
        ) {
            TextField(value =title, onValueChange = { title = it }, label = { Text("Job Title") })
            TextField(value =organizationName, onValueChange = { organizationName = it }, label = { Text("Organization Name") })
            TextField(value =category, onValueChange = { category = it }, label = { Text("Job Category") })
            TextField(value =skillsRequired, onValueChange = { skillsRequired = it }, label = { Text("Skills Required") })
            val statuses = listOf("Applied", "Interview", "Offer", "Rejected")
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                Text("Status", style = MaterialTheme.typography.headlineSmall)
                statuses.forEach { statusOption ->
                    Row(modifier =Modifier.padding(8.dp)) {
                        RadioButton(
                            selected =(status == statusOption),
                            onClick ={ status = statusOption }
                        )
                        Spacer(modifier =Modifier.width(8.dp))
                        Text(text =statusOption)
                    }
                }
            }

            Row {
                Button(onClick = {
                    if (title.isBlank() || organizationName.isBlank() ||
                        category.isBlank() || skillsRequired.isBlank()) {

                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Please fill in all fields")
                        }
                    } else {
                        val jobAction = Job(
                            id = jobId?.toInt() ?: 0,
                            title = title,
                            organizationName = organizationName,
                            category = category,
                            skillsRequired = skillsRequired.split(",").map { it.trim() },
                            status = status
                        )

                        if (jobId != null) {
                            viewModel.updateJob(jobAction)
                        } else {
                            viewModel.postJob(jobAction)
                        }
                        title = ""
                        organizationName = ""
                        category = ""
                        skillsRequired = ""
                        status = "Applied"
                        onJobPosted()
                    }
                }) {
                    Text(if (jobId != null) "Update Job" else "Post Job")
                }
                Spacer(modifier = Modifier.width(50.dp))
                Button(onClick = {navController.navigate("joblisting") }) {
                    Text(text = "Jobs")
                }
            }
        }
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.TopCenter))
    }
}
