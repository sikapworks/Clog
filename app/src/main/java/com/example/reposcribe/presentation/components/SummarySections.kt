package com.example.reposcribe.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.reposcribe.data.local.Summary
import com.example.reposcribe.domain.model.PromptResponse

@Composable
fun SummarySections(summary: PromptResponse) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (summary.newFeatures.isNotEmpty()) {
            Text("New Features:", style = MaterialTheme.typography.titleMedium)
            summary.newFeatures.forEach { Text("• $it") }
        }
        if (summary.improvements.isNotEmpty()) {
            Text("Improvements:", style = MaterialTheme.typography.titleMedium)
            summary.improvements.forEach { Text("• $it") }
        }
        if (summary.bugFixes.isNotEmpty()) {
            Text("Bug Fixes:", style = MaterialTheme.typography.titleMedium)
            summary.bugFixes.forEach { Text("• $it") }
        }
        if (summary.documentation.isNotEmpty()) {
            Text("Documentation:", style = MaterialTheme.typography.titleMedium)
            summary.documentation.forEach { Text("• $it") }
        }
    }
}