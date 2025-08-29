-m package com.example.reposcribe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.compose.RepoScribeTheme
import com.example.reposcribe.navigation.AppNavHost
import com.example.reposcribe.presentation.screens.DashboardScreen
import com.example.reposcribe.presentation.screens.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RepoScribeTheme {
//                AppNavHost()
                SettingsScreen(onLogout = {})
            }
        }
    }
}
