package com.example.reposcribe.domain.usecase

import com.example.reposcribe.domain.model.ConnectedRepo
import com.example.reposcribe.domain.repository.ConnectedRepoRepository
import javax.inject.Inject

class GetConnectedReposUseCase @Inject constructor(
    private val repo: ConnectedRepoRepository
) {
    suspend operator fun invoke(userId: String): List<ConnectedRepo> {
        return repo.getConnectedRepos(userId)
    }
}