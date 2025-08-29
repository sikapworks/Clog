package com.example.reposcribe.domain.usecase

import com.example.reposcribe.domain.model.ConnectedRepo
import com.example.reposcribe.domain.repository.ConnectedRepoRepository
import javax.inject.Inject

class AddConnectedRepoUseCase @Inject constructor(
    private val repository: ConnectedRepoRepository
) {
    suspend operator fun invoke(userId: String, repo: ConnectedRepo) {
        return repository.addConnectedRepo(userId, repo)
    }
}