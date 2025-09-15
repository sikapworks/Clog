package com.example.reposcribe.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryContent(
    repoName: String,
    period: String,
    commits: Int,
    contributors: Int,
    categories: Map<String, List<String>>, // key = category, value = list of items
    onShareClick: () -> Unit = {},
    onExportClick: () -> Unit = {},
    onRefreshClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(repoName) },
                actions = {
                    IconButton(onClick = onRefreshClick) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            //Subtitle

            Text(
                text = "Weekly Summary",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
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
                    elevation = ButtonDefaults.buttonElevation(6.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Share Summary")
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onExportClick,
                    elevation = ButtonDefaults.buttonElevation(6.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Export PDF")
                }
            }
        }
    }
}