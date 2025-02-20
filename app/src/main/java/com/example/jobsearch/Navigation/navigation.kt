package com.example.jobsearch.navigation



import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jobsearch.Screens.LoginScreen
import com.example.jobsearch.Screens.RecruiterLoginScreen
import com.example.jobsearch.Screens.RegistrationScreen
import com.example.jobsearch.Screens.WelcomeScreen

import com.example.jobsearch.Screens.recruiterregestartion
import com.example.jobsearch.Viewmodel.RegistrationViewModel
import com.example.jobsearch.screens.EmployeeJobListScreen
import com.example.jobsearch.screens.JobPostScreen
import com.example.jobsearch.screens.RecruiterJobListScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: RegistrationViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(
                onEmployeeSelected = { navController.navigate("employeeLogin") },
                onRecruiterSelected = { navController.navigate("recruiterLogin") }
            )
        }
        composable("registration") {
            RegistrationScreen(
                onRegisterSuccess = { navController.navigate("EmployeeJobListScreen") },
                onLoginSelected = { navController.navigate("employeeLogin") },
            )
        }
        composable("employeeLogin") {
            LoginScreen(
                viewModel = viewModel,
                navController = navController,

                onRegisterSelected = { navController.navigate("registration") }
            )
        }
        composable("recruiterregistration") {
            recruiterregestartion(
                onRegisterSuccess = { navController.navigate("recruiterJobPost") },
                onLoginSelected = { navController.navigate("recruiterLogin") }
            )
        }
        composable("recruiterLogin") {
            RecruiterLoginScreen(
                viewModel = viewModel,
                navController = navController,
                onRegisterSelected = { navController.navigate("recruiterregistration") }
            )
        }
        composable("joblisting") {
            RecruiterJobListScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composable("recruiterJobPost") {
            JobPostScreen(
                viewModel = hiltViewModel(),
                onJobPosted = { navController.navigate("joblisting") },
                navController = navController
            )
        }
        composable("job_post_screen/{jobId}") { backStackEntry ->
            val jobId = backStackEntry.arguments?.getString("jobId")
            JobPostScreen(
                viewModel = hiltViewModel(),
                onJobPosted = { navController.navigate("joblisting") },
                jobId = jobId,
                navController
            )
        }
        composable("EmployeeJobListScreen") {
            Log.e("App Test", "navugating")
            EmployeeJobListScreen(
                viewModel = hiltViewModel(),
                navController
            )
        }


    }
}
