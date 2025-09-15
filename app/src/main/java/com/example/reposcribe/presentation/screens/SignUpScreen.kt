package com.example.reposcribe.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reposcribe.presentation.components.AppTextField
import com.example.reposcribe.presentation.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onGoToLogin: () -> Unit,
    onSuccess: () -> Unit,
) {
    val state = viewModel.uiState

    if (state.success) {
        LaunchedEffect(Unit) { onSuccess() }
    }
    val scrollableState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.Center)
                .scrollable(
                    state = scrollableState,
                    orientation = Orientation.Vertical
                ),
        ) {
            Text(
                "Create Account",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(16.dp))

            // Email
            AppTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChanged,
                label = "Email",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            //Password
            AppTextField(
                value = state.password,
                onValueChange = viewModel::onPasswordChanged,
                label = "Password",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            // Username
            AppTextField(
                value = state.githubUsername,
                onValueChange = viewModel::onGithubUsernameChanged,
                label = "Github Username",
                modifier = Modifier.fillMaxWidth(),
                imeAction = ImeAction.Done
            )
            Spacer(Modifier.height(16.dp))

            //Signup
            Button(
                onClick = { viewModel.doSignUp() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading
            ) {
                Text(if (state.loading) "Creating..." else "Sign up")
            }

            Spacer(Modifier.height(8.dp))
            //Login
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Already have an account? ",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium

                )
                Spacer(Modifier.width(4.dp))
                Text(
                    "Login",
                    modifier = Modifier.clickable(onClick = onGoToLogin),
                    color = MaterialTheme.colorScheme.onBackground,
                    textDecoration = TextDecoration.Underline,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            //show error message if present
            state.error?.let {
                Spacer(Modifier.height(8.dp))
                Text(text = it.toString(), color = MaterialTheme.colorScheme.error)
            }

        }
    }
}