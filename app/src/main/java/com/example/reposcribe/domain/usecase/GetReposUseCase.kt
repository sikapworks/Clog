package com.example.reposcribe.domain.usecase

import com.example.reposcribe.domain.model.Repo
import com.example.reposcribe.domain.repository.GithubRepository
import javax.inject.Inject

class GetReposUseCase @Inject constructor(
    private val githubRepository: GithubRepository
) {
    suspend operator fun invoke(username: String): List<Repo> =
        githubRepository.getUserRepos(username)
}