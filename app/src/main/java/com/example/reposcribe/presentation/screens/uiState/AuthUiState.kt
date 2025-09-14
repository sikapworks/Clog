package com.example.reposcribe.presentation.screens.uiState

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val githubUsername: String = "",
    val success: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null,
)