package com.example.reposcribe.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {
    suspend fun createUser(
        email: String,
        password: String,
        githubUsername: String
    ): Pair<String, String> {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val uid = result.user?.uid ?: error("UID null after signup")

        // store github username in FireStore under users/{uid}
        db.collection("users").document(uid)
            .set(mapOf("githubUsername" to githubUsername, "email" to email))
            .await()
        return uid to email
    }

    suspend fun signIn(email: String, password: String): Pair<String, String> {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        val uid = result.user?.uid ?: error("UID null after login")
        val safeEmail = result.user?.email ?: email
        return uid to safeEmail
    }

    suspend fun fetchGithubUsername(uid: String): String? {
        val fetch = db.collection("users").document(uid).get().await()
        return fetch.getString("githubUsername")
    }

    fun currentUid(): String? = auth.currentUser?.uid

    fun signOut() = auth.signOut()
}