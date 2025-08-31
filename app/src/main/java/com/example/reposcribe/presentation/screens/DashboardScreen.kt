package com.example.reposcribe.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reposcribe.domain.model.ConnectedRepo
import com.example.reposcribe.presentation.components.ConnectRepoDialog
import com.example.reposcribe.presentation.components.EmptyState
import com.example.reposcribe.presentation.components.ErrorState
import com.example.reposcribe.presentation.components.RepoRow
import com.example.reposcribe.presentation.components.RepoSkeleton
import com.example.reposcribe.presentation.uiState.FetchRepoState
import com.example.reposcribe.presentation.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onOpenSettings: () -> Unit,
    onRepoClick: (owner: String, name: String) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
//    val state = viewModel.uiState
    val state = viewModel.uiState.collectAsState().value
    var showDialog by remember { mutableStateOf(false) }

    //diff btw by and =
    val refreshing = state is FetchRepoState.Loading
    val refreshState = rememberPullToRefreshState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                actions = {
                    IconButton(onOpenSettings) {
                        Icon(Icons.Outlined.Settings, null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Connect Repo")
            }
        }
    )
    { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
//
        ) {
            when (state) {
                FetchRepoState.Idle, FetchRepoState.Loading -> repeat(4) { RepoSkeleton() }
                is FetchRepoState.Error -> ErrorState(state.message, onRetry = viewModel::refresh)
                is FetchRepoState.Success -> {
                    val list = state.data
                    if (list.isEmpty()) {
                        EmptyState(
                            title = "No repositories yet",
                            subtitle = "Connect your first repo to start getting summaries",
                            actionText = "Connect Repo",
                            onAction = {
                                viewModel.addRepo(
                                    ConnectedRepo.from("sikapworks", "RepoScribe")
                                )
                            }
                        )
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(list) { repo ->
                                RepoRow(
                                    repo = repo,
                                    onClick = {
                                        val owner = viewModel.githubUsername ?: return@RepoRow
                                        onRepoClick(owner, repo.name)
                                    }
                                )
                            }

                        }
                    }
                }

            }
//            PullRefreshIndicator(refreshing, refreshState, Modifier.align(Alignment.TopCenter))
        }

    }
    if (showDialog) {
        ConnectRepoDialog(
            onDismiss = { showDialog = false },
            onConfirm = { owner, name ->
//                val repo = ConnectedRepo.from(owner, name)
                viewModel.connectRepo(owner, name)
                showDialog = false
            }
        )
    }

}