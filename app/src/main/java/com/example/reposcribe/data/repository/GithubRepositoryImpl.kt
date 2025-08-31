package com.example.reposcribe.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.reposcribe.data.mapper.toDomain
import com.example.reposcribe.data.remote.GithubApiService
import com.example.reposcribe.domain.model.Commit
import com.example.reposcribe.domain.model.ConnectedRepo
import com.example.reposcribe.domain.model.Repo
import com.example.reposcribe.domain.repository.GithubRepository
import javax.inject.Inject

//hilt can provide this repo wherever needed
class GithubRepositoryImpl @Inject constructor(
    private val apiService: GithubApiService
) : GithubRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getUserRepos(username: String): List<Repo> {
        return apiService.getUserRepos(username).map { it.toDomain() }
    }

    override suspend fun getCommitsForRange(
        owner: String,
        repo: String,
        sinceIso: String,
        untilIso: String
    ): List<Commit> {
        Log.d("GithubRepository", "Calling API for $owner/$repo since=$sinceIso until=$untilIso")
        val response = apiService.getCommitsForRange(owner, repo, sinceIso, untilIso)
        Log.d("GithubRepository", "Fetched commits: ${response.size}")
        response.forEach {
            Log.d("GithubRepository", "commit message: ${it.commit.message}")
        }
        return response.map { it.toDomain() }
    }

    override suspend fun getConnectedRepos(userId: String): List<ConnectedRepo> {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchRepoDetails(
        owner: String,
        name: String
    ): Repo {
        return apiService.getRepoDetails(owner, name).toDomain()
    }
}