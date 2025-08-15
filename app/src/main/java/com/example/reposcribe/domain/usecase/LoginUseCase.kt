package com.example.reposcribe.domain.usecase

import com.example.reposcribe.domain.model.User
import com.example.reposcribe.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return authRepository.login(email, password)
    }
}