package com.example.reposcribe.domain.usecase

import com.example.reposcribe.domain.repository.AuthRepository

class UpdateGithubUsernameUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(uid: String, newUsername: String) {
        authRepository.updateGithubUsername(uid, newUsername)
    }
}