package com.example.reposcribe.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.RepoScribeTheme
import com.example.reposcribe.presentation.components.ProfileCard
import com.example.reposcribe.presentation.components.SettingsOption
import com.example.reposcribe.presentation.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val user = viewModel.user.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Settings") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (user != null || user == null) {
                ProfileCard(
                    username = user?.githubUsername.toString() ?: "sikap",
                    email = user?.email ?: "ksiya1306@gmail.com"
                )
            }

            Divider()

            SettingsOption(
                title = "Theme",
                subtitle = "System default",
                onClick = { /* open theme dialog */ TODO() }
            )

            SettingsOption(
                title = "Notifications",
                toggle = true,
                onToggleChange = { enabled ->
                    viewModel.updateNotifications(enabled)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.logout()
                    onLogout()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Logout")
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    RepoScribeTheme {
        SettingsScreen(
            onLogout = {}
        )
    }

}
