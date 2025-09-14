package com.example.reposcribe.presentation.screens.uiState

sealed class FetchRepoState<out T> {
    object Idle: FetchRepoState<Nothing>()  //initial state
    object Loading: FetchRepoState<Nothing>()   //when API call is in progress
    data class Success<T>(val data: T): FetchRepoState<T>()    // when API call succeeds
    data class Error(val message: String): FetchRepoState<Nothing>()  //when API call fails
}