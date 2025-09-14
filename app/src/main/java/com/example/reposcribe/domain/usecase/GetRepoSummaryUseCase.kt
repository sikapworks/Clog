package com.example.reposcribe.domain.usecase

import android.util.Log
import com.example.reposcribe.domain.model.PromptRequest
import com.example.reposcribe.domain.model.PromptResponse
import com.example.reposcribe.domain.repository.AiRepository
import javax.inject.Inject

class GetRepoSummaryUseCase @Inject constructor(
    private val repository: AiRepository
) {
    suspend operator fun invoke(request: PromptRequest): PromptResponse {
        Log.d("GetRepoSummaryUC", "Invoked with request contents=${request.contents.size}")
        val response = repository.getSummary(request)
        Log.d("GetRepoSummaryUC", "RepoSummary result: $response")
        return response
    }
}