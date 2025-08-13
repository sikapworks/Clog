package com.example.reposcribe.presentation.signup

//Holds UI state

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val githubUsername: String = "",
    val success: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)