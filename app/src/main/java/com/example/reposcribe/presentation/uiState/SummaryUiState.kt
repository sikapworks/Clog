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