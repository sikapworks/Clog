package com.example.reposcribe.data.remote.dto

data class AiSummaryRequest(
    val contents: List<Content>
)

data class Content(
    val role: String,     //"system" or "user"
    val parts: List<Part>
)

data class Part(
    val text: String
)