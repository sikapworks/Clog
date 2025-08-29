package com.example.reposcribe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reposcribe.domain.usecase.GetWeeklyCommitsUseCase
import com.example.reposcribe.presentation.uiState.AuthUiState
import com.example.reposcribe.presentation.uiState.FetchRepoState
import com.example.reposcribe.presentation.uiState.WeeklySummaryUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val getWeeklyCommits: GetWeeklyCommitsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<FetchRepoState<WeeklySummaryUi>>(FetchRepoState.Idle)
    val uiState: MutableStateFlow<FetchRepoState<WeeklySummaryUi>> = _uiState

    fun load(owner: String, repo: String) {
        viewModelScope.launch {
            _uiState.value = FetchRepoState.Loading
//            try {
//                val = curr
//            }
        }
    }
}