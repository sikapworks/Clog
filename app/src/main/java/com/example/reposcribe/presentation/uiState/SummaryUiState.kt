package com.example.reposcribe.presentation.uiState

data class SummarySectionUi (
    val title: String,
    val points: List<String>
)

data class WeeklySummaryUi (
    val weekTitle: String,
    val commitsCount: Int,
    val contributorsCount: Int,
    val sections: List<SummarySectionUi>
)

data class SummaryUiState(
    val isLoading: Boolean = false,
    val isFetchingCommits: Boolean = false,
    val summary: String? = null,
    val error: String? = null
)