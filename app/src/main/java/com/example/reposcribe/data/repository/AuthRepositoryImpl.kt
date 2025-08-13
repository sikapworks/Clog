package com.example.reposcribe.data.repository

import com.example.reposcribe.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

//This class knows HOW to actually sign up the user and save extra info
//does not know about UI or business logic


//implements AuthRepository from domain
class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
): AuthRepository {

    override suspend fun signup(
        email: String,
        password: String,
        githubUsername: String
    ): Result<Unit> {
        return try {
            //create a user in firebase auth
            val result = auth.createUserWithEmailAndPassword(email, password).await()

            // get the user's UID from firebase
            val userId = result.user?.uid ?: return Result.failure(Exception("User ID is null"))

            //store the github username in firestore under users/{uid}
            db.collection("users").document(userId)
                .set(mapOf("githubUsername" to githubUsername))
                .await()
            Result.success(Unit)
        }
        catch (e: Exception) {
            Result.failure(e) // return the exception
        }
    }
}