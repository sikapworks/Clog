package com.example.reposcribe.domain.model

data class PromptResponse (
    val newFeatures: List<String> = emptyList(),
    val improvements: List<String> = emptyList(),
    val bugFixes: List<String> = emptyList(),
    val documentation: List<String> = emptyList()
)