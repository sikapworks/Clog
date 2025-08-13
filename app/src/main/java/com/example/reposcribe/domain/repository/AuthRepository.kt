package com.example.reposcribe.domain.repository

//Abstract repository interface


interface AuthRepository {
    suspend fun signup(
        email: String,
        password: String,
        githubUsername: String
    ): Result<Unit>
}