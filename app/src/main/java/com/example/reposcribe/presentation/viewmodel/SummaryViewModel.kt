package com.example.reposcribe.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reposcribe.domain.model.Commit
import com.example.reposcribe.domain.model.PromptRequest
import com.example.reposcribe.domain.usecase.GetRepoSummaryUseCase
import com.example.reposcribe.domain.usecase.GetWeeklyCommitsUseCase
import com.example.reposcribe.presentation.uiState.SummaryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val getWeeklyCommits: GetWeeklyCommitsUseCase,
    private val getRepoSummary: GetRepoSummaryUseCase
) : ViewModel() {

    private val _commits = MutableStateFlow<List<Commit>>(emptyList())
    val commits: StateFlow<List<Commit>> = _commits

    private val _uiState = MutableStateFlow(SummaryUiState())
    val uiState: StateFlow<SummaryUiState> = _uiState

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadCommits(owner: String, repo: String) {
        viewModelScope.launch {
            try {
                _uiState.value = SummaryUiState(isLoading = true)
                Log.d("SummaryVM", "Fetching commits for $owner/$repo")
                val data = getWeeklyCommits(owner, repo)
                _commits.value = data
                _uiState.value = _uiState.value.copy(isLoading = false)
                Log.d("SummaryVM", "Got ${data.size} commits")
            } catch (e: Exception) {
                Log.e("SummaryVM", "Error fetching commits", e)
                _uiState.value = SummaryUiState(error = e.message ?: "Error fetching commits")
            }
        }
    }

    fun loadSummary(prompt: String) {
        viewModelScope.launch {
            try {
                _uiState.value = SummaryUiState(isLoading = true)

                val request = PromptRequest(
                    contents = listOf(
                        PromptRequest.Content(
                            role = "user",
                            parts = listOf(
                                PromptRequest.Content.Part(text = prompt)
                            )
                        )
                    )
                )
                Log.d("SummaryVM", "Fetching summary with prompt: $prompt")

                val response = getRepoSummary(request)
                _uiState.value = SummaryUiState(summary = response.summary)
            } catch (e: Exception) {
                Log.e("SummaryVM", "Error fetching summary", e)
                _uiState.value = SummaryUiState(error = e.message ?: "Error fetching summary")
            }
        }
    }
}
