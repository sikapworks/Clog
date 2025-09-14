package com.example.reposcribe.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reposcribe.presentation.components.SummarySections
import com.example.reposcribe.presentation.viewmodel.SummaryViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SummaryScreen(
    owner: String,
    repo: String,
//    userId: String,
    onBack: () -> Unit,
    viewModel: SummaryViewModel = hiltViewModel()
) {
    val commits by viewModel.commits.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(owner, repo) {
        viewModel.loadCommits(owner, repo, true)
//        viewModel.loadSummary("Summarize the weekly activity of repo $repo owned by $owner.")  // call Ai summary
    }

    Column {
        Text(text = "Summary for $owner/$repo")

        Button(onClick = {viewModel.refresh(owner, repo)}) {
            Text("Refresh")
        }

        when {
            uiState.isLoading -> Text("Loading...")
            uiState.isFetchingCommits -> Text("Fetching commits, this may take a while.")
            uiState.error != null -> Text("Error: ${uiState.error}")
            uiState.summary != null -> SummarySections(summary = uiState.summary!!)
        }
//        if(commits.isNotEmpty()) {
//            Text("Commits this week:")
//            commits.forEach { commit ->
//                Text("> ${commit.message}")
//            }
//        }
    }
}
