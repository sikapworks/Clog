package com.example.reposcribe.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reposcribe.domain.model.Repo
import com.example.reposcribe.presentation.components.RepoCard
import com.example.reposcribe.presentation.screens.uiState.FetchRepoState
import com.example.reposcribe.presentation.viewmodel.RepoViewModel

@Composable
fun RepositoryScreen(
    viewModel: RepoViewModel = hiltViewModel(),
    onRepoClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAddRepoClick() }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Connect Repository")
            }
        }
    ) { padding ->
        when (uiState) {
            is FetchRepoState.Idle -> {
                TODO()
            }

            is FetchRepoState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            }

            is FetchRepoState.Success<*> -> {
                val repos = (uiState as FetchRepoState.Success<List<Repo>>).data
                LazyColumn {
                    items(repos) { repo ->
                        RepoCard(repo = repo, onClick = { onRepoClick(repo.id.toString()) })
                    }
                }
            }

            is FetchRepoState.Error -> {
                Text(
                    (uiState as FetchRepoState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

    }
}
