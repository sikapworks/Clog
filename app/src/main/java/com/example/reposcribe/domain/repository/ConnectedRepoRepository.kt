package com.example.reposcribe.domain.repository

import com.example.reposcribe.domain.model.ConnectedRepo

interface ConnectedRepoRepository {
    suspend fun getConnectedRepo(userId: String): List<ConnectedRepo>
    suspend fun addConnectedRepo(userId: String, repo: ConnectedRepo)
    suspend fun removeConnectedRepo(userId: String, repoId: String)
    suspend fun isRepoConnected(userId: String, repoId: String): Boolean
//    repoId -> owner/name
}