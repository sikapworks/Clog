package com.example.reposcribe.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reposcribe.data.remote.dto.RepositoryDto
import com.example.reposcribe.domain.model.Repo
import com.example.reposcribe.domain.repository.GithubRepository
import com.example.reposcribe.presentation.uiState.FetchRepoState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RepoViewModel(
    private val repository: GithubRepository
) : ViewModel() {

    //holds current ui state
    private val _uiState =
        MutableStateFlow<FetchRepoState<List<Repo>>>(FetchRepoState.Idle)
    val uiState: MutableStateFlow<FetchRepoState<List<Repo>>> = _uiState

    init {
        viewModelScope.launch {
            try {
                val repos = repository.getUserRepos("sikapworks")
                if (repos.isNotEmpty()) {
                    val firstRepo = repos[0]
                    val commits = repository.getCommitsForRange("sikapworks", firstRepo.name,               "2025-01-01T00:00:00Z", "2025-08-01T00:00:00Z" )
                    Log.d("GithubViewModel", "Commits for ${firstRepo.name}: ${commits.size}")
                }
            } catch (e: Exception) {
                Log.e("GithubViewModel", "Init failed: ${e.message}")
            }
        }
    }

    fun fetchRepos(username: String) {
        viewModelScope.launch {
            _uiState.value = FetchRepoState.Loading
            try {
                val repos = repository.getUserRepos(username)
                _uiState.value = FetchRepoState.Success(repos)

            } catch (e: Exception) {
                _uiState.value = FetchRepoState.Error(e.message.toString())
//                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun onAddRepoClick() {
        TODO("Not yet implemented")
    }
}