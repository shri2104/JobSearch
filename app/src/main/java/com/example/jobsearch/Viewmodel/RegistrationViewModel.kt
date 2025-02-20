package com.example.jobsearch.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.jobsearch.Entity.User
import com.example.jobsearch.Repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _registrationState = MutableStateFlow<Boolean?>(null)
    val registrationState: StateFlow<Boolean?> = _registrationState

    private val _loginState = MutableStateFlow<Boolean?>(null)
    val loginState: StateFlow<Boolean?> = _loginState

    fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        organization: String,
        address: String
    ) {
        viewModelScope.launch {
            val user = User(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password,
                organizationName = organization,
                address = address
            )
            val success = userRepository.registerUser(user)
            _registrationState.value = success
        }
    }
    fun isEmailTaken(email: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val exists = userRepository.checkEmailExists(email)
            onResult(exists)
        }
    }
    fun login(email: String, password: String, userType: String, navController: NavController) {
        viewModelScope.launch {
            val success = userRepository.login(email, password)
            if (success) {
                when (userType) {
                    "Employee" -> navController.navigate("EmployeeJobListScreen")
                    "Recruiter" -> navController.navigate("recruiterJobPost")
                }
            } else {
                _loginState.value = success
            }
        }
    }
}