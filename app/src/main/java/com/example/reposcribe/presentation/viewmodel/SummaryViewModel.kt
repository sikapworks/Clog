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

    private var isCommitsLoaded = false

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadCommits(owner: String, repo: String, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                _uiState.value = SummaryUiState(isLoading = forceRefresh)
                Log.d("SummaryVM", "Fetching commits for $owner/$repo")
                val result = getWeeklyCommits(owner, repo)
                _commits.value = result
                isCommitsLoaded = forceRefresh

                _uiState.value = _uiState.value.copy(isLoading = false)
                Log.d("SummaryVM", "Got ${result.size} commits")

            } catch (e: Exception) {
                Log.e("SummaryVM", "Error fetching commits", e)
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun refresh(owner: String, repo: String) {
        loadCommits(owner, repo, forceRefresh = true)
        loadSummary("Summarize weekly activity for repo $repo owned by $owner.")
    }

    fun loadSummary(prompt: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

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

                val summary = getRepoSummary(request)
                _uiState.value = _uiState.value.copy(isLoading = false, summary = summary.toString())

            } catch (e: Exception) {
                Log.e("SummaryVM", "Error fetching summary", e)
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }
}
