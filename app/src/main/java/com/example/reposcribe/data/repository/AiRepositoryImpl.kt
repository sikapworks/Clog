package com.example.reposcribe.data.repository

import android.util.Log
import com.example.reposcribe.data.remote.AiApiService
import com.example.reposcribe.data.remote.dto.AiSummaryRequest
import com.example.reposcribe.data.remote.dto.Content
import com.example.reposcribe.data.remote.dto.GenerationConfig
import com.example.reposcribe.data.remote.dto.Part
import com.example.reposcribe.domain.model.PromptRequest
import com.example.reposcribe.domain.model.PromptResponse
import com.example.reposcribe.domain.repository.AiRepository
//import com.example.reposcribe.BuildConfig
import com.google.gson.Gson
import javax.inject.Inject

class AiRepositoryImpl @Inject constructor(
    private val apiService: AiApiService
) : AiRepository {

    private val apiKey = "AIzaSyBe6C5oXOpM8YZhV8sbnNJkOKnEImRKsMo"

    init {
        Log.d("AiRepositoryImpl", "Loaded API key: $apiKey")
    }

    override suspend fun getSummary(request: PromptRequest): PromptResponse {

        //map domain -> dto
        val aiRequest = AiSummaryRequest(
            contents = request.contents.map { content ->
                Content(
                    role = content.role,
                    parts = content.parts.map { Part(text = it.text) }
                )
            },
            generationConfig = GenerationConfig(responseMineType = "application/json")
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
            ?.content?.parts?.firstOrNull()?.text
            ?: "No response"

        return try {
            Gson().fromJson(summaryText, PromptResponse::class.java)
        } catch (e: Exception) {
            PromptResponse()  //fallback empty
        }
    }
}