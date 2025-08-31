package com.example.reposcribe.domain.model


data class PromptRequest(
    val contents: List<Content>
) {
    data class Content (
        val role: String = "user",
        val parts: List<Part>
    ) {
        data class Part(
            val text: String
        )
    }
}