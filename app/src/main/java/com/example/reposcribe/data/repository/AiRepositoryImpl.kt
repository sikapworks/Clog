package com.example.reposcribe.data.repository

import com.example.reposcribe.data.remote.AiApiService
import com.example.reposcribe.data.remote.dto.AiSummaryRequest
import com.example.reposcribe.data.remote.dto.Content
import com.example.reposcribe.data.remote.dto.Part
import com.example.reposcribe.domain.model.PromptRequest
import com.example.reposcribe.domain.model.PromptResponse
import com.example.reposcribe.domain.repository.AiRepository
import javax.inject.Inject

class AiRepositoryImpl @Inject constructor(
    private val apiService: AiApiService
) : AiRepository {

    override suspend fun getSummary(request: PromptRequest): PromptResponse {

        //map domain -> dto
        val aiRequest = AiSummaryRequest(
            contents = request.contents.map { content ->
                Content(
                    role = content.role,
                    parts = content.parts.map { Part(text = it.text) }
                )
            }
        )

        //call API
        val response = apiService.getSummary(
            model = "gemini-2.0-flash",
            apiKey = "API_KEY",
            request = aiRequest
        )

        //map dto -> domain
        val summaryText = response.candidates
            .firstOrNull()
            ?.content?.parts?.firstOrNull()?.text ?: "No response"

        return PromptResponse(summaryText)
    }
}