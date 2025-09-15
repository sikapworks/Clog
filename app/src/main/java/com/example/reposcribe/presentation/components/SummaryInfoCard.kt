package com.example.reposcribe.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SummaryInfoCard(period: String, commits: Int, contributors: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            InfoItem("Period", period, Icons.Default.DateRange)

            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                InfoItem("Commits", commits.toString(), Icons.Default.Build)
                InfoItem("Contributors", contributors.toString(), Icons.Default.Person)
            }
        }
    }
}

@Preview
@Composable
fun CardsPreview() {
    SummaryInfoCard(
        "12/03/2025 - 13/03/2025",
        5,
        1
    )
}