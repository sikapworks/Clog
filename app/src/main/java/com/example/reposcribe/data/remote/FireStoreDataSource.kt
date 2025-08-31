package com.example.reposcribe.data.remote

import com.example.reposcribe.domain.model.ConnectedRepo
import com.google.firebase.Firebase
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
            .collection("connectedRepos")
            .get()
            .await()

        return snapshot.documents.mapNotNull { it.toObject(ConnectedRepo::class.java) }    //???
    }
    suspend fun addConnectedRepo(userId: String, repo: ConnectedRepo) {
        usersCollection
            .document(userId)
            .collection("connectedRepos")
            .document(repo.repoId)
            .set(repo)
            .await()
    }
    suspend fun removeConnectedRepo(userId: String, repoId: String) {
        usersCollection
            .document(userId)
            .collection("connectedRepos")
            .document(repoId)
            .delete()
            .await()
    }
    suspend fun isRepoConnected(userId: String, repoId: String): Boolean {
        val doc = usersCollection
            .document(userId)
            .collection("connectedRepos")
            .document(repoId)
            .get()
            .await()

        return doc.exists()
    }
}