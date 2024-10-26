package com.example.jobsearch.Screens

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jobsearch.Viewmodel.RegistrationViewModel

@Composable
fun recruiterregestartion(
    firstName: String = "",
    lastName: String = "",
    email: String = "",
    password: String = "",
    organization: String = "",
    address: String = "",
    onRegisterSuccess: () -> Unit = {},
    onLoginSelected: () -> Unit = {},
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    var firstNameState by remember { mutableStateOf(firstName) }
    var lastNameState by remember { mutableStateOf(lastName) }
    var emailState by remember { mutableStateOf(email) }
    var passwordState by remember { mutableStateOf(password) }
    var organizationState by remember { mutableStateOf(organization) }
    var addressState by remember { mutableStateOf(address) }
    var errorMessage by remember { mutableStateOf("") }

    // Use a Box to center content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Recruiter Registration")
            TextField(
                value = firstNameState,
                onValueChange = { firstNameState = it },
                label = { Text("First Name") }
            )
            TextField(
                value = lastNameState,
                onValueChange = { lastNameState = it },
                label = { Text("Last Name") }
            )
            TextField(
                value = emailState,
                onValueChange = { emailState = it },
                label = { Text("Email") }
            )
            TextField(
                value = passwordState,
                onValueChange = { passwordState = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            TextField(
                value = organizationState,
                onValueChange = { organizationState = it },
                label = { Text("Organization Name") }
            )
            TextField(
                value = addressState,
                onValueChange = { addressState = it },
                label = { Text("Address") }
            )

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {

                    errorMessage = ""

                    // Field validation
                    when {
                        firstNameState.isBlank() || lastNameState.isBlank() ||
                                emailState.isBlank() || passwordState.isBlank() ||
                                organizationState.isBlank() || addressState.isBlank() -> {
                            errorMessage = "Please fill all the fields"
                        }
                        !Patterns.EMAIL_ADDRESS.matcher(emailState).matches() -> {
                            errorMessage = "Please enter a valid email (e.g., abc@gmail.com)"
                        }
                        passwordState.length < 8 -> {
                            errorMessage = "Password must be at least 8 characters long"
                        }
                        else -> {
                            viewModel.isEmailTaken(emailState) { isTaken ->
                                if (isTaken) {
                                    errorMessage = "Email is already in use. Please use a different email."
                                } else {
                                    viewModel.registerUser(
                                        firstName = firstNameState,
                                        lastName = lastNameState,
                                        email = emailState,
                                        password = passwordState,
                                        organization = organizationState,
                                        address = addressState
                                    )
                                    onRegisterSuccess()
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Register")
            }

            Button(onClick = onLoginSelected, modifier = Modifier.padding(top = 16.dp)) {
                Text("Already have an account? Login")
            }
        }
    }
}

@Composable
@Preview
fun RecruiterRegistrationPreview() {
    recruiterregestartion(
        onRegisterSuccess = {},
        onLoginSelected = {}
    )
}
