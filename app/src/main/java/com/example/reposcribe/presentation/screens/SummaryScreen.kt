package com.example.reposcribe.presentation.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.RepoScribeTheme
import com.example.reposcribe.presentation.components.SummaryContent
import com.example.reposcribe.presentation.viewmodel.SummaryViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SummaryScreen(
    owner: String,
    repo: String,
    onBack: () -> Unit,
    viewModel: SummaryViewModel = hiltViewModel()
) {
    // observe ui + commits from viewModel
    val uiState by viewModel.uiState.collectAsState()
    val commits by viewModel.commits.collectAsState()

    // trigger load once when opened
    LaunchedEffect(owner, repo) {
        viewModel.loadCommits(owner, repo, fetchSummary = true)
    }

    // derive period from commit ISO dates (simple substring approach)
    fun isoDatePrefix(iso: String?): String? {
        if (iso.isNullOrBlank()) return null
        return try {
            // expect format "yyyy-MM-dd..." so substring first 10 chars
            iso.substring(0, minOf(iso.length, 10))
        } catch (e: Exception) {
            null
        }
    }

    val dates = commits.mapNotNull { isoDatePrefix(it.dateIso) }.sorted()
    val period = if (dates.isNotEmpty()) "${dates.first()} - ${dates.last()}" else "N/A"

    val commitsCount = commits.size
    val contributorsCount =
        commits.mapNotNull { it.authorName?.takeIf { it.isNotBlank() } }.toSet().size

    // build categories map only for non-empty lists
    val categories = linkedMapOf<String, List<String>>()
    uiState.summary?.let { s ->
        if (s.newFeatures.isNotEmpty()) categories["New Features"] = s.newFeatures
        if (s.improvements.isNotEmpty()) categories["Improvements"] = s.improvements
        if (s.bugFixes.isNotEmpty()) categories["Bug Fixes"] = s.bugFixes
        if (s.documentation.isNotEmpty()) categories["Documentation"] = s.documentation
    }
    if (categories.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("No summary available yet. Tap Refresh to generate.")
            Button(onClick = { TODO() }) {
                Text("Refresh")
            }
        }
    } else {
        SummaryContent(
            repoName = "$owner/$repo",
            period = period,
            commits = commits.size,
            contributors = contributorsCount,
            categories = categories,
            onShareClick = TODO(),
            onExportClick = TODO(),
        )
    }

    // debug
    Log.d(
        "SummaryRoute",
        "owner=$owner repo=$repo commits=$commitsCount contributors=$contributorsCount period=$period"
    )
    Log.d(
        "SummaryRoute",
        "uiState.summary present=${uiState.summary != null}, categories=${categories.keys}"
    )
//        if (commits.isNotEmpty()) {
//            Text("Commits this week:")
//            commits.forEach { commit ->
//                Text("-----> ${commit.message}")
//            }
//        }
}


@Preview(showBackground = true)
@Composable
fun PreviewSummaryScreen() {
    val fakeCategories = linkedMapOf(
        "New Features" to listOf("Dark mode added", "New dashboard screen"),
        "Bug Fixes" to listOf(
            "Fixed crash on login",
            "Resolved API timeout",
            "Dark mode added",
            "New dashboard screen",
            "Fixed crash on login",
            "Resolved API timeout",
            "Dark mode added",
            "New dashboard screen",
            "Fixed crash on login",
            "Resolved API timeout",
            "Dark mode added",
            "New dashboard screen"
        ),
        "Documentation" to listOf("Updated README", "Added API usage guide")
    )

    RepoScribeTheme {
        SummaryContent(
            repoName = "Clog",
            period = "2025-09-01 - 2025-09-07",
            commits = 42,
            contributors = 5,
            categories = fakeCategories
        )
    }
}
