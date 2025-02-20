package com.example.jobsearch.Screens

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Recruiter Registration", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            InputField(label = "First Name", value = firstNameState, onValueChange = { firstNameState = it }, placeholder = "Enter First Name")
            InputField(label = "Last Name", value = lastNameState, onValueChange = { lastNameState = it }, placeholder = "Enter Last Name")
            InputField(label = "Email", value = emailState, onValueChange = { emailState = it }, placeholder = "Enter Email")
            InputField(label = "Password", value = passwordState, onValueChange = { passwordState = it }, placeholder = "Enter Password")
            InputField(label = "Organization Name", value = organizationState, onValueChange = { organizationState = it }, placeholder = "Enter Organization Name")
            InputField(label = "Address", value = addressState, onValueChange = { addressState = it }, placeholder = "Enter Address")

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    errorMessage = ""
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
fun InputField(label: String, value: String, onValueChange: (String) -> Unit, placeholder: String) {
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

@Composable
@Preview
fun RecruiterRegistrationPreview() {
    recruiterregestartion(
        onRegisterSuccess = {},
        onLoginSelected = {}
    )
}
