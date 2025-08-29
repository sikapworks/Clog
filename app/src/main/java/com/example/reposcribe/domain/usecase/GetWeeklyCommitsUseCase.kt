package com.example.reposcribe.domain.usecase

import com.example.reposcribe.domain.model.Commit
import com.example.reposcribe.domain.repository.GithubRepository
import javax.inject.Inject

class GetWeeklyCommitsUseCase @Inject constructor(
    private val githubRepository: GithubRepository
) {
    suspend operator fun invoke(
        owner: String,
        name: String,
        sinceIso: String,
        untilIso: String
    ): List<Commit> {
        return githubRepository.getCommitsForRange(owner, name, sinceIso, untilIso)
    }
}