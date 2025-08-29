package com.example.reposcribe.data.remote

import androidx.compose.ui.tooling.data.SourceContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.firestoreSettings
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
        return try {
            val doc = db.collection("users").document(uid)
                .get(Source.CACHE)  // first try cache
                .await()
            if (doc.exists() && doc.getString("githubUsername") != null) {
                doc.getString("githubUsername")!!
            }
            else {
                //fallback to server if cache is empty
                db.collection("users").document(uid)
                    .get(Source.SERVER)
                    .await()
                    .getString("githubUsername") ?: ""
            }
        }
        catch (e: Exception) {
            e.message //fallback safely, avoid crash
        }
    }

    suspend fun updateGithubUsername(uid: String, newUsername: String) {
        val userDoc = db.collection("users").document(uid)
        userDoc.update("githubUsername", newUsername).await()
    }

    fun checkIfUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun currentUid(): String? = auth.currentUser?.uid

    fun signOut() = auth.signOut()

    fun fetchEmail(uid: String): String? = auth.currentUser?.email
}