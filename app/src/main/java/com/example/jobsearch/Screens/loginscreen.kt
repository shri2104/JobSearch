package com.example.jobsearch.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jobsearch.Viewmodel.RegistrationViewModel

@Composable
fun LoginScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    navController: NavController,
    onRegisterSelected: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val loginState by viewModel.loginState.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Employee Login")
            TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            Button(onClick = {
                viewModel.login(email, password, "Employee", navController)
            }, modifier = Modifier.padding(top = 16.dp)) {
                Text("Login")
            }
            if (loginState == false) {
                errorMessage = "Invalid email or password!"
            }
            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, modifier = Modifier.padding(top = 16.dp))
            }
            Button(onClick = onRegisterSelected, modifier = Modifier.padding(top = 16.dp)) {
                Text("Don't have an account? Register")
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen(
//        onLoginSuccess = {},
//        onRegisterSelected = {}
//    )
//}