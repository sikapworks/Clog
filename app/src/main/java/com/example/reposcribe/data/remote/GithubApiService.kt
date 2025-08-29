package com.example.reposcribe.data.remote

import com.example.reposcribe.data.remote.dto.CommitListItemDto
import com.example.reposcribe.data.remote.dto.RepositoryDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("users/{username}/repos")   // performs HTTP GET request
    suspend fun getUserRepos(     // coroutine function, needs to be called from viewModelScope.launch{...}
        @Path("username") username: String   //replaces username in the URL
    ): List<RepositoryDto>   // returns a list of DTOs parsed from JSON by the Gson converter

    @GET("repos/{owner}/{repo}/commits")
    suspend fun getCommitsForRange(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("since") sinceIso: String,
        @Query("until") untilIso: String
    ): List<CommitListItemDto>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepoDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): RepositoryDto
}