package com.example.reposcribe.domain.usecase

import com.example.reposcribe.domain.model.User
import com.example.reposcribe.domain.repository.AuthRepository

//lint checker -> code quality inspector

class GetCurrentUserUseCase(private val authRepository: AuthRepository) {
    //    @SuppressLint("RestrictedApi") // annotation tells the lint checker to ignore warnings about using restricted or internal APIs
    suspend operator fun invoke(): User? = authRepository.getCurrentUser()
}