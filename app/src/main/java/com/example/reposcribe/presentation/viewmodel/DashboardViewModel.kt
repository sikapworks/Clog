package com.example.reposcribe.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reposcribe.domain.model.ConnectedRepo
import com.example.reposcribe.domain.model.Repo
import com.example.reposcribe.domain.usecase.AddConnectedRepoUseCase
import com.example.reposcribe.domain.usecase.GetConnectedReposUseCase
import com.example.reposcribe.domain.usecase.GetCurrentUserUseCase
import com.example.reposcribe.domain.usecase.GetRepoDetailsUseCase
import com.example.reposcribe.domain.usecase.GetReposUseCase
import com.example.reposcribe.domain.usecase.IsRepoConnectedUseCase
import com.example.reposcribe.domain.usecase.RemoveConnectedRepoUseCase
import com.example.reposcribe.presentation.uiState.FetchRepoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUserUseCase,
    private val getConnectedRepos: GetConnectedReposUseCase,
    private val addConnectedRepo: AddConnectedRepoUseCase,
    private val removeConnectedRepo: RemoveConnectedRepoUseCase,
    private val getRepoDetails: GetRepoDetailsUseCase,
    private val isRepoConnected: IsRepoConnectedUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<FetchRepoState<List<Repo>>>(FetchRepoState.Idle)
    val uiState: StateFlow<FetchRepoState<List<Repo>>> = _uiState

    var githubUsername: String? = null
        private set

    init {
        loadRepos()
    }

    private fun loadRepos() {
        viewModelScope.launch {
            _uiState.value = FetchRepoState.Loading
            try {
                val user = getCurrentUser()
                if (user == null) {
                    _uiState.value = FetchRepoState.Success(emptyList())
                    return@launch      //?
                }
                githubUsername = user.githubUsername

                val connected = getConnectedRepos(user.uid) //get repos from firestore not api
                if (connected.isEmpty()) {
                    _uiState.value = FetchRepoState.Error("No connected repositories found.")
                } else {
                    // map each ConnectedRepo -> Repo by fetching details
                    val repos = connected.map {
                        getRepoDetails(it.owner, it.name)
                    }
                    _uiState.value = FetchRepoState.Success(repos)
                }
            }
            catch (e: Exception) {
                _uiState.value = FetchRepoState.Error(e.message ?: "Failed to load repositories")
            }
        }
    }

    fun refresh() = loadRepos()

    fun connectRepo(owner: String, name: String) {
        viewModelScope.launch {
            val user = getCurrentUser()
            if (user == null) {
                _uiState.value = FetchRepoState.Error("No logged in user.")
                return@launch
            }
            try {
//                verify repo exists on GitHub
                val details = getRepoDetails(owner, name)  // calls API

//              save into firestore
                val connectedRepo = ConnectedRepo(owner, name)
                addConnectedRepo(user.uid, connectedRepo)

                // reload repos
                loadRepos()

                Log.d("DashboardVM", "Repo connected: ${connectedRepo.repoId}")
            } catch (e: Exception) {
                Log.e("DashboardVM", "Failed to connect repo", e)
                _uiState.value = FetchRepoState.Error("Failed to connect: ${e.message}")
            }
        }
    }


    fun addRepo(repo: ConnectedRepo) {
        viewModelScope.launch {
            val user = getCurrentUser()
            if (user != null) {
                addConnectedRepo(user.uid, repo)  // writes to firestore
                loadRepos()   //reload repos
            }
        }
    }

    fun removeRepo(repoId: String) {
        viewModelScope.launch {
            val user = getCurrentUser()
            if (user != null) {
                removeConnectedRepo(user.uid, repoId)
                loadRepos()
            }
        }
    }
}