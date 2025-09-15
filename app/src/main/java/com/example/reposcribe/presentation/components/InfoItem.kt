package com.example.reposcribe.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun InfoItem(label: String, value: String, icon: ImageVector) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(icon, contentDescription = label, tint = MaterialTheme.colorScheme.onTertiaryContainer)
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = label,
            fontSize = 12.sp,
        )
    }
}