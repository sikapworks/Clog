package com.example.reposcribe.data.repository

import android.util.Log
import com.example.reposcribe.BuildConfig
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

    private val apiKey = BuildConfig.API_KEY

//    init {
//        Log.d("AiRepositoryImpl", "Load api key $apiKey")
//    }

    override suspend fun getSummary(request: PromptRequest): PromptResponse {

        //map domain -> dto
        val aiRequest = AiSummaryRequest(
            contents = request.contents.map { content ->
                Content(
//                    role = "user",
//                    parts = listOf(Part(text = "Say hello in JSON."))
                    role = content.role,
                    parts = content.parts.map { Part(text = it.text) }
                )
            },
//            generationConfig = GenerationConfig(responseMineType = "application/json")
        )


        //call API
        val response = apiService.getSummary(
            model = "gemini-2.0-flash",
            apiKey = apiKey,
            request = aiRequest
        )
        Log.d("AiRepositoryImpl", "Full response: ${Gson().toJson(response)}")


        //map dto -> domain
        val summaryText = response.candidates
            .firstOrNull()
            ?.content?.parts?.firstOrNull()?.text
            ?: "No response"
        Log.d("AiRepositoryImpl", "Raw AI response: $summaryText")

        return try {
            val json = summaryText.substringAfter("{").substringBeforeLast("}")
            val cleanJson = "{$json}"
            val parsed = Gson().fromJson(cleanJson, PromptResponse::class.java)
            return parsed
        } catch (e: Exception) {
            PromptResponse()  //fallback empty
        }
        Log.d("AiRepositoryImpl", "Raw AI response: $summaryText")

    }
}