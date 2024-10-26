package com.example.jobsearch.Viewmodel

import androidx.lifecycle.*
import com.example.jobsearch.Repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val loginState = MutableLiveData<Boolean>()
    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = userRepository.login(email, password)
            loginState.postValue(user != null)
        }
    }
}
