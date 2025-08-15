package com.example.reposcribe.domain.usecase

import com.example.reposcribe.domain.model.User
import com.example.reposcribe.domain.repository.AuthRepository

class SignUpUseCase(
    private val authRepository: AuthRepository
) {
    // Executes the signup process by delegating to AuthRepository
    //invoke is a keyword
    suspend operator fun invoke(
        email: String,
        password: String,
        githubUsername: String
    ): Result<User> {
        return authRepository.signup(email, password, githubUsername)
    }
}