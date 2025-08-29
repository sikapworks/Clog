package com.example.reposcribe.domain.usecase

import com.example.reposcribe.domain.model.ConnectedRepo
import com.example.reposcribe.domain.repository.ConnectedRepoRepository
import javax.inject.Inject

class IsRepoConnectedUseCase @Inject constructor(
    private val repo: ConnectedRepoRepository
) {
    suspend operator fun invoke(userId: String, repoId: String): Boolean {
        return repo.isRepoConnected(userId, repoId)
    }
}