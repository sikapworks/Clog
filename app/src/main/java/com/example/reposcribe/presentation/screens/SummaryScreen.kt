package com.example.reposcribe.presentation.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
        Log.d(
            "SummaryScreen",
            "Composed SummaryScreen owner=$owner repo=$repo uiStateSummaryPresent=${uiState.summary != null}"
        )
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

    // UI rendering: if a summary exists show it; else friendly empty state
    when {
        uiState.isLoading || uiState.isFetchingCommits -> Text("Loading...")
        uiState.error != null -> Text("Error: ${uiState.error}")
        uiState.summary != null -> {
            SummaryContent(
                repoName = "$owner/$repo",
                period = period,
                commits = commitsCount,
                contributors = contributorsCount,
                categories = categories
            )
        }

        else -> {
            Column {
                Text("No summary available yet. Tap Refresh to generate.")
                Button(onClick = { viewModel.refresh(owner, repo) }) {
                Text("Refresh")
                }
            }
        }
    }
}

