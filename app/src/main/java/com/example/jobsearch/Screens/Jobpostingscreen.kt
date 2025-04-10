package com.example.jobsearch.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jobsearch.entity.Job
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jobsearch.viewmodel.JobViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
    val status = "Applied"
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
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (jobId != null) "Update Job" else "Post a Job") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {

                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
            ) {
                InputFields(label = "Job Title", value = title, onValueChange = { title = it }, placeholder = "Enter job title")
                InputFields(label = "Organization Name", value = organizationName, onValueChange = { organizationName = it }, placeholder = "Enter organization name")
                InputFields(label = "Job Category", value = category, onValueChange = { category = it }, placeholder = "Enter job category")
                InputFields(label = "Skills Required", value = skillsRequired, onValueChange = { skillsRequired = it }, placeholder = "Enter required skills")

                Row {
                    Button(onClick = {
                        if (title.isBlank() || organizationName.isBlank() || category.isBlank() || skillsRequired.isBlank()) {
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
                            onJobPosted()
                        }
                    }) {
                        Text(if (jobId != null) "Update Job" else "Post Job")
                    }

                    Spacer(modifier = Modifier.width(50.dp))

                    Button(onClick = { navController.navigate("joblisting") }) {
                        Text(text = "Jobs")
                    }
                }
            }
        }
    }
}


@Composable
fun InputFields(label: String, value: String, onValueChange: (String) -> Unit, placeholder: String) {
    Text(
        text = label,
        fontSize = 16.sp,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Blue,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
    }
}