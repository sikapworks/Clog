package com.example.reposcribe.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.reposcribe.presentation.viewmodel.SplashScreenViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    LaunchedEffect(isUserLoggedIn) {
        delay(800)
        when (isUserLoggedIn) {
            true -> navController.navigate("repos") {
                popUpTo("splash") { inclusive = true }
            }

            false -> navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }

            else -> Unit //loading
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
//            TODO() App logo

            // App name
            Text(
                text = ">_Clog",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            // Tagline under the name
            Text(
                text = "Your very own commit logs",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}