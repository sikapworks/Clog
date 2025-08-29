package com.example.reposcribe.domain.usecase

import com.example.reposcribe.domain.model.Repo
import com.example.reposcribe.domain.repository.GithubRepository
import javax.inject.Inject

class GetRepoDetailsUseCase @Inject constructor(
    private val githubRepository: GithubRepository
) {
    suspend operator fun invoke(owner: String, name: String): Repo {
        return githubRepository.fetchRepoDetails(owner, name)
    }
}