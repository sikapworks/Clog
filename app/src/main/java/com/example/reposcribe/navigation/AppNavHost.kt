package com.example.reposcribe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reposcribe.presentation.screens.LoginScreen
import com.example.reposcribe.presentation.screens.RepositoryHolderScreen
import com.example.reposcribe.presentation.screens.SignUpScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                onSuccess = {
                    navController.navigate("repos") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },
                onGoToSignup = { navController.navigate("signup") },
            )
        }

        composable("signup") {
            SignUpScreen(
                onSuccess = {
                    navController.navigate("repos") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },
            )
        }

        composable("repos") {
            RepositoryHolderScreen(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("repos") {
                            inclusive = true
                        }
                    }
                }
            )

        }
    }
}