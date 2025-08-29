package com.example.reposcribe.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reposcribe.domain.model.User
import com.example.reposcribe.domain.repository.AuthRepository
import com.example.reposcribe.domain.usecase.GetCurrentUserUseCase
import com.example.reposcribe.domain.usecase.UpdateGithubUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getCurrentUse: GetCurrentUserUseCase,
    private val authRepository: AuthRepository,
    private val updateGithubUsername: UpdateGithubUsernameUseCase
): ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        viewModelScope.launch {
            _user.value = getCurrentUse()
        }
    }

    fun updateGithubUsername(newUsername: String) {
        viewModelScope.launch {
            val user = _user.value
            if (user != null) {
                updateGithubUsername(user.uid, newUsername)
                _user.value = user.copy(githubUsername = newUsername)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logOut()
        }
    }

    fun updateNotifications(enabled: Boolean) {
        // save to datastore later
        TODO()
    }

}