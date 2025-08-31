package com.example.reposcribe.data.remote

import com.example.reposcribe.data.remote.dto.AiSummaryRequest
import com.example.reposcribe.data.remote.dto.AiSummaryResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AiApiService {

    @Headers("Content-Type: application/json")
    @POST("v1beta/models/{model}:generateContent")
    suspend fun getSummary(
        @Path("model") model: String,
        @Query("key") apiKey: String,
        @Body request: AiSummaryRequest,
    ): AiSummaryResponse
}