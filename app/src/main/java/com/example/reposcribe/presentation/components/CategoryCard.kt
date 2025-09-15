package com.example.reposcribe.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CategoryCard(category: String, items: List<String>) {

    var expanded by remember { mutableStateOf(false) }
    val visibleItems = if (expanded) items else items.take(1)

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Badge() {
                    Text("${items.size}")
                }
            }

            Spacer(Modifier.height(8.dp))

            visibleItems.forEach { item ->
                Text("• $item")
            }

            if (items.size > 2) {
                TextButton(onClick = { expanded = !expanded }) {
                    Text(if (expanded) "Show less" else "Show ${items.size - 2} more items")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    CategoryCard(
        "New Features",
        items = listOf(
            "Implemented dark mode toggle",
            "Realtime notifications via WebSocket",
            "Add user onboarding flow"
        )
    )
}