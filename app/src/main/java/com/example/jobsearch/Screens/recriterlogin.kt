package com.example.jobsearch.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jobsearch.Viewmodel.RegistrationViewModel

@Composable
fun RecruiterLoginScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    navController: NavController,
    onRegisterSelected: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val loginState by viewModel.loginState.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Recruiter Login", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            InputField(
                label = "Email",
                value = email,
                onValueChange = { email = it },
                placeholder = "Enter your email"
            )

            InputField(
                label = "Password",
                value = password,
                onValueChange = { password = it },
                placeholder = "Enter your password"
            )

            Button(onClick = {
                viewModel.login(email, password, "Recruiter", navController)
            }, modifier = Modifier.padding(top = 16.dp)) {
                Text("Login")
            }

            if (loginState == false) {
                errorMessage = "Invalid email or password!"
            }

            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, modifier = Modifier.padding(top = 16.dp), color = Color.Red)
            }

            Button(onClick = onRegisterSelected, modifier = Modifier.padding(top = 16.dp)) {
                Text("Don't have an account? Register")
            }
        }
    }
}
