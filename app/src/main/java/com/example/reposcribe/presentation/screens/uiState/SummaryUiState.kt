package com.example.reposcribe.presentation.screens.uiState

import com.example.reposcribe.domain.model.PromptResponse

data class SummaryUiState(
    val isLoading: Boolean = false,
    val isFetchingCommits: Boolean = false,
    val summary: PromptResponse? = null,
    val error: String? = null,
    val period: String = "N/A",
    val contributorsCount: Int = 0,
    val commitsCount: Int = 0
)