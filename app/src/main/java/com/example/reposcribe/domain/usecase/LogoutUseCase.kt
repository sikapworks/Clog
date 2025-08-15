package com.example.reposcribe.domain.usecase

import com.example.reposcribe.domain.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        return authRepository.logOut()
    }
}