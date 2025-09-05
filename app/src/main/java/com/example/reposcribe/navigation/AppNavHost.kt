package com.example.reposcribe.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.reposcribe.presentation.screens.DashboardScreen
import com.example.reposcribe.presentation.screens.LoginScreen
import com.example.reposcribe.presentation.screens.SettingsScreen
import com.example.reposcribe.presentation.screens.SignUpScreen
import com.example.reposcribe.presentation.screens.SplashScreen
import com.example.reposcribe.presentation.screens.SummaryScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(navController)
        }

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
            DashboardScreen(
                onOpenSettings = { navController.navigate("settings") },
                onRepoClick = { owner, name ->
                    navController.navigate("summary/$owner/$name")
                }
            )
        }

        composable(
            "summary/{owner}/{name}",
            arguments = listOf(
                navArgument("owner") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType }
            )
        ) { backStack ->
            val owner = backStack.arguments?.getString("owner")!!
            val name = backStack.arguments?.getString("name")!!
            Log.d("NavGraph", "Navigated to summary with $owner/$name")
            SummaryScreen(
                owner = owner,
                repo = name,
                onBack = { navController.popBackStack() })
        }

        composable("settings") {
            SettingsScreen(onLogout = { navController.navigate("login") })
        }
    }
}