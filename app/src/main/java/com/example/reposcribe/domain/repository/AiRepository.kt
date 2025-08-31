package com.example.reposcribe.domain.repository

import com.example.reposcribe.domain.model.PromptRequest
import com.example.reposcribe.domain.model.PromptResponse

interface AiRepository {
    suspend fun getSummary(request: PromptRequest): PromptResponse

}
