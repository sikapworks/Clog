package com.example.reposcribe.domain.repository

import com.example.reposcribe.domain.model.User

//Abstract repository interface
interface AuthRepository {
    suspend fun signup(email: String, password: String, githubUsername: String): Result<User>
    suspend fun login(email: String, password: String): Result<User>
    suspend fun logOut()
    suspend fun getCurrentUser(): User?
    fun checkIfUserLoggedIn(): Boolean
    suspend fun updateGithubUsername(uid: String, newUsername: String)

}