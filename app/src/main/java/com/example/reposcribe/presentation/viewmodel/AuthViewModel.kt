package com.example.reposcribe.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reposcribe.domain.usecase.GetCurrentUserUseCase
import com.example.reposcribe.domain.usecase.LoginUseCase
import com.example.reposcribe.domain.usecase.LogoutUseCase
import com.example.reposcribe.domain.usecase.SignUpUseCase
import com.example.reposcribe.presentation.uiState.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signUp: SignUpUseCase,
    private val login: LoginUseCase,
    private val logOut: LogoutUseCase,
    private val currentUser: GetCurrentUserUseCase
) : ViewModel() {

    //holds all fields and UI state
    var uiState by mutableStateOf(AuthUiState())
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

    fun doSignUp() {
        if (uiState.email.isBlank() || uiState.password.isBlank() || uiState.githubUsername.isBlank()) {
            uiState = uiState.copy(error = "All fields are required")
            return
        }
        uiState = uiState.copy(loading = true, error = null)
        viewModelScope.launch {
            signUp(uiState.email, uiState.password, uiState.githubUsername)
                .onSuccess { uiState = uiState.copy(loading = false, success = true) }
                .onFailure { e -> uiState = uiState.copy(loading = false, error = e.message) }
        }
    }

    fun doLogin() {
        if (uiState.email.isBlank() || uiState.password.isBlank()) {
            uiState = uiState.copy(error = "Email and password is required")
            return
        }
        uiState = uiState.copy(loading = true, error = null)
        viewModelScope.launch {
            login(uiState.email, uiState.password)
                .onSuccess { uiState = uiState.copy(loading = false, success = true) }
                .onFailure { e -> uiState = uiState.copy(loading = false, error = e.message) }
        }
    }

    fun doLogout() {
        viewModelScope.launch {
            logOut()
            uiState = AuthUiState()
        }
    }

    fun checkIfUserLoggedIn() {
        viewModelScope.launch {
            val user = currentUser()
            if (user != null) {
                uiState = uiState.copy(success = true)
            }
        }
    }
}