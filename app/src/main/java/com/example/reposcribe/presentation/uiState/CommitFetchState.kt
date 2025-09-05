package com.example.reposcribe.presentation.uiState

sealed class CommitFetchState {
    object Idle : CommitFetchState()
    object Loading : CommitFetchState()
    data class Success(val commitCount: Int): CommitFetchState()
    data class Error(val message: String): CommitFetchState()
}