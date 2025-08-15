package com.example.reposcribe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.reposcribe.presentation.components.AppTextField
import com.example.reposcribe.presentation.screens.LoginScreen
import com.example.reposcribe.presentation.screens.SignUpScreen
import com.example.reposcribe.ui.theme.RepoScribeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RepoScribeTheme {
                App()
//                LoginScreen(onSuccess = {}, onGoToSignup = {})
            }
        }
    }
}
