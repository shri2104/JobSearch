package com.example.jobsearch.Screens

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jobsearch.Viewmodel.RegistrationViewModel

@Composable
fun RegistrationScreen(
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
            Text(text = "Employee's Registration")
            InputField(label = "First Name", value = firstNameState, onValueChange = { firstNameState = it }, placeholder = "Enter First Name")
            InputField(label = "Last Name", value = lastNameState, onValueChange = { lastNameState = it }, placeholder = "Enter Last Name")
            InputField(label = "Email", value = emailState, onValueChange = { emailState = it }, placeholder = "Enter Email")
            InputField(label = "Password", value = passwordState, onValueChange = { passwordState = it }, placeholder = "Enter Password")
            InputField(label = "Organization Name", value = organizationState, onValueChange = { organizationState = it }, placeholder = "Enter Organization Name")
            InputField(label = "Address", value = addressState, onValueChange = { addressState = it }, placeholder = "Enter Address")

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = androidx.compose.material3.MaterialTheme.colorScheme.error)
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
                                    errorMessage = "Email is already registered"
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
fun RegistrationScreenPreview() {
    RegistrationScreen(
        onRegisterSuccess = {},
        onLoginSelected = {}
    )
}
