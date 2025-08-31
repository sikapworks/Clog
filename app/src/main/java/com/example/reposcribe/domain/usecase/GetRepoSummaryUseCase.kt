package com.example.reposcribe.domain.usecase

import com.example.reposcribe.domain.model.PromptRequest
import com.example.reposcribe.domain.model.PromptResponse
import com.example.reposcribe.domain.repository.AiRepository
import javax.inject.Inject

class GetRepoSummaryUseCase @Inject constructor(
    private val repository: AiRepository
) {
    suspend operator fun invoke(request: PromptRequest): PromptResponse {
        return repository.getSummary(request)
    }
}