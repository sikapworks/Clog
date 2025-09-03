package com.example.reposcribe.data.remote

import com.example.reposcribe.domain.model.ConnectedRepo
import com.example.reposcribe.domain.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireStoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val usersCollection = firestore.collection("users")

    suspend fun getConnectedRepo(userId: String): List<ConnectedRepo> {
        val snapshot = usersCollection
            .document(userId)
            .get()
            .await()
        val user = snapshot.toObject(User::class.java)
        return user?.connectedRepos ?: emptyList()
    }

    suspend fun addConnectedRepo(userId: String, repo: ConnectedRepo) {
        usersCollection
            .document(userId)
            .update("connectedRepos", FieldValue.arrayUnion(repo))
            .await()
    }

    suspend fun removeConnectedRepo(userId: String, repoId: String) {
        val snapshot = usersCollection
            .document(userId)
            .get()
            .await()
        val user = snapshot.toObject(User::class.java)
        val currentRepos = user?.connectedRepos ?: emptyList()

        val toRemove = currentRepos.find { it.repoId == repoId }
        if(toRemove != null) {
            usersCollection
                .document(userId)
                .update("connectedRepos", FieldValue.arrayRemove(toRemove))
                .await()
        }
    }

    suspend fun isRepoConnected(userId: String, repoId: String): Boolean {
        val repos = getConnectedRepo(userId)
        return repos.any { it.repoId == repoId }
    }
}