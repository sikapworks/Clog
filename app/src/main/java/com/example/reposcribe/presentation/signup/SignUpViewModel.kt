package com.example.reposcribe.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.PasswordCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reposcribe.domain.usecase.SignUpUseCase
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase
): ViewModel() {

    //holds all fields and UI state
    var uiState by mutableStateOf(SignUpUiState())
        private set

    // called when email field is changed
    fun onEmailChanged(email: String) {
        uiState = uiState.copy(email = email)
    }

    // called when password field is changed
    fun onPasswordChanged(password: String) {
        uiState = uiState.copy(password = password)
    }

    // called when Github username is changed
    fun onGithubUsernameChanged(githubUsername: String) {
        uiState = uiState.copy(githubUsername = githubUsername)
    }

    fun signUp() {
        if(uiState.email.isBlank() || uiState.password.isBlank() || uiState.githubUsername.isBlank()) {
            uiState = uiState.copy(error = "All fields are required")
            return
        }
        viewModelScope.launch {
            val result = signUpUseCase(uiState.email, uiState.password, uiState.githubUsername)
            result.onSuccess {
                uiState = uiState.copy(success = true, error = null)
            }.onFailure {
                uiState = uiState.copy(error = it.message)
            }
        }

    }
}