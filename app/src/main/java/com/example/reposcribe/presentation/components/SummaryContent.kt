package com.example.reposcribe.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SummaryContent(
    repoName: String,
    period: String,
    commits: Int,
    contributors: Int,
    categories: Map<String, List<String>>, // key = category, value = list of items
    onShareClick: () -> Unit = {},
    onExportClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Repo Title + Subtitle
        Text(
            text = repoName,
            style = MaterialTheme.typography.headlineSmall,
//            color = Color.Yellow
        )
        Text(
            text = "Weekly Summary",
            style = MaterialTheme.typography.bodyMedium,
//            color = Color.LightGray
        )

        Spacer(Modifier.height(16.dp))

        // Period + Commits + Contributors card
        SummaryInfoCard(period, commits, contributors)

        Spacer(Modifier.height(16.dp))

        // Category sections
        categories.forEach { (category, items) ->
            CategoryCard(category = category, items = items)
            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.height(24.dp))

        // Action buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onShareClick,
//                colors = ButtonDefaults.buttonColors(Color(0xFFFFC107))
            ) {
                Text("Share Summary")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onExportClick,
//                colors = ButtonDefaults.buttonColors(Color(0xFF673AB7))
            ) {
                Text("Export PDF")
            }
        }
    }
}