package com.example.reposcribe.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reposcribe.presentation.components.AppTextField
import com.example.reposcribe.presentation.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onSuccess: () -> Unit,
) {
    val state = viewModel.uiState

    if (state.success) {
        LaunchedEffect(Unit) { onSuccess() }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.Center),
        ) {
            Text(text = "Create account", style = MaterialTheme.typography.headlineSmall)
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
                modifier = Modifier.fillMaxWidth()
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

            //show error message if present
            state.error?.let {
                Spacer(Modifier.height(8.dp))
                Text(text = it.toString(), color = MaterialTheme.colorScheme.error)
            }

        }
    }
}