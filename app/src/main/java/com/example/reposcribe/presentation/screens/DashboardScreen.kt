package com.example.reposcribe.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reposcribe.presentation.components.ConnectRepoDialog
import com.example.reposcribe.presentation.components.EmptyState
import com.example.reposcribe.presentation.components.ErrorState
import com.example.reposcribe.presentation.components.RepoRow
import com.example.reposcribe.presentation.components.RepoSkeleton
import com.example.reposcribe.presentation.screens.uiState.CommitFetchState
import com.example.reposcribe.presentation.screens.uiState.FetchRepoState
import com.example.reposcribe.presentation.viewmodel.DashboardViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onOpenSettings: () -> Unit,
    onRepoClick: (owner: String, name: String) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
//    val state = viewModel.uiState
    val state = viewModel.uiState.collectAsState().value
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val availableRepos = viewModel.availableRepos.collectAsState().value
    val commitState = viewModel.commitState.collectAsState().value

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
            FloatingActionButton(onClick = {
                viewModel.loadAvailableRepos()
                showDialog = true
            }) {
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
                                viewModel.loadAvailableRepos()
                                showDialog = true
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
            repos = availableRepos,
            onDismiss = { showDialog = false },
            onConfirm = { repo ->
                val owner = viewModel.githubUsername ?: return@ConnectRepoDialog
                viewModel.connectRepo(owner, repo.name)
                showDialog = false
            }
        )
    }
    when (commitState) {
        is CommitFetchState.Loading -> {
            Text("Fetching commits... This may take a while.")
        }

        is CommitFetchState.Error -> {
            ErrorState(commitState.message, onRetry = { TODO() })
        }

        is CommitFetchState.Success -> {
            Text("Fetched ${commitState.commitCount} commits successfully")
        }

        else -> Unit
    }
}