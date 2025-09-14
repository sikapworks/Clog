package com.example.reposcribe.presentation.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reposcribe.domain.model.Commit
import com.example.reposcribe.domain.model.PromptRequest
import com.example.reposcribe.domain.model.PromptResponse
import com.example.reposcribe.domain.repository.CommitRepository
import com.example.reposcribe.domain.repository.SummaryRepository
import com.example.reposcribe.domain.usecase.GetRepoSummaryUseCase
import com.example.reposcribe.domain.usecase.GetWeeklyCommitsUseCase
import com.example.reposcribe.domain.utils.builtCommitSummaryPrompt
import com.example.reposcribe.presentation.screens.uiState.SummaryUiState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val getWeeklyCommits: GetWeeklyCommitsUseCase,
    private val getRepoSummary: GetRepoSummaryUseCase,
    private val commitRepository: CommitRepository,
    private val summaryRepository: SummaryRepository
) : ViewModel() {

    private val _commits = MutableStateFlow<List<Commit>>(emptyList())
    val commits: StateFlow<List<Commit>> = _commits

    private val _uiState = MutableStateFlow(SummaryUiState())
    val uiState: StateFlow<SummaryUiState> = _uiState

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadCommits(owner: String, repo: String, fetchSummary: Boolean = false) {
        viewModelScope.launch {
            Log.d("SummaryVM", "loadCommits START for $owner/$repo fetchSummary=$fetchSummary")
            _uiState.value = _uiState.value.copy(isFetchingCommits = true, error = null)

            try {
                val fetchedCommits = getWeeklyCommits(owner, repo)

                // filter commits to last 7 days
                val now = LocalDate.now()
                val weekAgo = now.minusDays(6)
                val filtered = fetchedCommits.filter { commit ->
                    commit.dateIso?.let { LocalDate.parse(it.substring(0, 10)) }
                        ?.isAfter(weekAgo.minusDays(1)) ?: false
                }   // understanddd this

                _commits.value = filtered
                updateDerivedState(filtered, _uiState.value.summary)

                _uiState.value = _uiState.value.copy(isFetchingCommits = false)

                if (fetchSummary) {
                    val cached = summaryRepository.getLatestSummary(owner, repo)
                    if (cached != null) {
                        val summary =
                            Gson().fromJson(cached.summaryText, PromptResponse::class.java)
                        _uiState.value = _uiState.value.copy(isLoading = false, summary = summary)
                        updateDerivedState(filtered, summary)
                    } else {
                        loadSummary(owner, repo, fetchedCommits)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isFetchingCommits = false,
                    error = e.message
                )
            }
            Log.d("SummaryVM", "loadCommits END for $owner/$repo")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun refresh(owner: String, repo: String) {
        loadCommits(owner, repo, fetchSummary = true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadSummary(owner: String, repo: String, commits: List<Commit>) {
        viewModelScope.launch {
            Log.d("SummaryVM", "loadSummary START for $owner/$repo commits=${commits.size}")
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val (startDate, endDate) = (LocalDate.now().minusDays(6)
                    .toString() to LocalDate.now().toString())
                val commitMessages = _commits.value.map { it.message }
                val promptJson = builtCommitSummaryPrompt(repo, startDate, endDate, commitMessages)

                Log.d("SummaryVM", "Prompt JSON: ${promptJson.take(1000)}")

                val request = PromptRequest(
                    contents = listOf(
                        PromptRequest.Content(
                            role = "user",
                            parts = listOf(
                                PromptRequest.Content.Part(text = promptJson)
                            )
                        )
                    )
                )

                val summary = getRepoSummary(request)
                Log.d("SummaryVM", "AI summary response: $summary")

//                Save structured JSON back to Room
                val summaryJson = Gson().toJson(summary)
                summaryRepository.storeSummary(
                    owner = owner,
                    repo = repo,
                    summaryText = summaryJson,
                    generatedAt = System.currentTimeMillis()
                )

                _uiState.value = _uiState.value.copy(isLoading = false, summary = summary)
                Log.d("SummaryVM", "loadSummary SUCCESS for $owner/$repo")

            } catch (e: Exception) {
                Log.e("SummaryVM", "Error fetching summary", e)
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDerivedState(commits: List<Commit>, summary: PromptResponse?) {
        val dates = commits.mapNotNull { it.dateIso?.substring(0, 10) }.sorted()
        val period = if (dates.isNotEmpty()) "${dates.first()} - ${dates.last()}" else "N/A"

        val contributorsCount =
            commits.mapNotNull { it.authorName?.takeIf { it.isNotBlank() } }.toSet().size

        val commitsCount = summary?.let {
            it.newFeatures.size + it.improvements.size + it.bugFixes.size + it.documentation.size
        } ?: 0

        _uiState.value = _uiState.value.copy(
            period = period,
            contributorsCount = contributorsCount,
            commitsCount = commitsCount
        )
    }
}
