package com.example.reposcribe.domain.repository

import com.example.reposcribe.domain.model.Commit
import com.example.reposcribe.domain.model.ConnectedRepo
import com.example.reposcribe.domain.model.Repo

interface GithubRepository {
    suspend fun getUserRepos(username: String): List<Repo>
    suspend fun getCommitsForRange(
        owner: String,
        repo: String,
        sinceIso: String,
        untilIso: String
    ): List<Commit>
    suspend fun getConnectedRepos(userId: String): List<ConnectedRepo>
    suspend fun fetchRepoDetails(owner: String, name: String): Repo
}