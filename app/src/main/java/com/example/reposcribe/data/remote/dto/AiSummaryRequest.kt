package com.example.reposcribe.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AiSummaryRequest(
    val contents: List<Content>,
    val generationConfig: GenerationConfig? = null
)

data class Content(
    val role: String,     //"system" or "user"
    val parts: List<Part>
)

data class Part(
    val text: String
)

data class GenerationConfig(
    @SerializedName("response_mine_type") val responseMineType: String? = null
)