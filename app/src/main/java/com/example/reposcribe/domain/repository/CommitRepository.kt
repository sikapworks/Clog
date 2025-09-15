package com.example.reposcribe.domain.repository

import com.example.reposcribe.data.local.CommitDao
import com.example.reposcribe.data.mapper.toDomain
import com.example.reposcribe.data.mapper.toEntity
import com.example.reposcribe.data.remote.FireStoreDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommitRepository @Inject constructor(
    private val commitDao: CommitDao,
    private val firestore: FireStoreDataSource
) {
    suspend fun storeCommits(
        commits: List<com.example.reposcribe.domain.model.Commit>,
        owner: String,
        repoName: String,
        userId: String
    ) {
        val entities = commits.map { it.toEntity(owner, repoName) }
        commitDao.insertCommits(entities)  // Store in local Room DB

        val localCommits =
            commits.map { it.toEntity(owner, repoName) }   // map to data.local.commit
        firestore.storeCommits(userId, localCommits)    //store in firestore
    }

    suspend fun getStoredCommits(
        owner: String,
        repoName: String,
        userId: String
    ): List<com.example.reposcribe.domain.model.Commit> {
        val local = commitDao.getAllCommits(owner, repoName).map { it.toDomain() }

        return if (local.isNotEmpty()) local
        else firestore.getCommits(userId).map { it.toDomain() }
    }

    suspend fun clearCommits(owner: String, repoName: String, userId: String) {
        commitDao.deleteCommits(owner, repoName)
        firestore.clearCommits(userId)
    }
}