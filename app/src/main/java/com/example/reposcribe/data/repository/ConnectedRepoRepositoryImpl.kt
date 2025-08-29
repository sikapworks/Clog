package com.example.reposcribe.data.repository

import com.example.reposcribe.data.remote.FireStoreDataSource
import com.example.reposcribe.domain.model.ConnectedRepo
import com.example.reposcribe.domain.repository.ConnectedRepoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectedRepoRepositoryImpl @Inject constructor(
    private val firestore: FireStoreDataSource
): ConnectedRepoRepository {
    override suspend fun getConnectedRepo(userId: String): List<ConnectedRepo> {
        return firestore.getConnectedRepo(userId)
    }

    override suspend fun addConnectedRepo(
        userId: String,
        repo: ConnectedRepo
    ) {
        firestore.addConnectedRepo(userId, repo)
    }

    override suspend fun removeConnectedRepo(userId: String, repoId: String) {
        firestore.removeConnectedRepo(userId, repoId)
    }

    override suspend fun isRepoConnected(
        userId: String,
        repoId: String
    ): Boolean {
        return firestore.isRepoConnected(userId, repoId)
    }
}