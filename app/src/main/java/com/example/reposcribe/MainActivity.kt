package com.example.reposcribe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.compose.RepoScribeTheme
import com.example.reposcribe.navigation.AppNavHost
import com.example.reposcribe.presentation.screens.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("KeysCheck", "API_KEY: ${BuildConfig.API_KEY.take(5)}..., GITHUB_TOKEN: ${BuildConfig.GITHUB_TOKEN.take(5)}...")

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RepoScribeTheme {
                AppNavHost()
//                SettingsScreen(onLogout = {})
            }
        }
    }
}
