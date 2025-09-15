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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.reposcribe.presentation.components.AppTextField
import com.example.reposcribe.presentation.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onSuccess: () -> Unit,
    onGoToSignup: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
    val scrollableState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.checkIfUserLoggedIn()
    }

    if (state.success) {
        LaunchedEffect(Unit) {
            onSuccess()
        }
    }
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
                )
        ) {
            Text(
                "Welcome back!",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(16.dp))

            // Email
            AppTextField(
                value = state.email,
                onValueChange = viewModel::onEmailChanged,
                placeholder = "Email",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            //Password
            AppTextField(
                value = state.password,
                onValueChange = viewModel::onPasswordChanged,
                placeholder = "Password",
                modifier = Modifier.fillMaxWidth(),
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            )
            Spacer(Modifier.height(16.dp))

            //Login Button
            Button(
                onClick = { viewModel.doLogin() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(if (state.loading) "Logging in..." else "Login")
            }
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Don't have an account? ",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium

                )
                Spacer(Modifier.width(4.dp))
                Text(
                    "Create Account",
                    modifier = Modifier.clickable(onClick = onGoToSignup),
                    color = MaterialTheme.colorScheme.onBackground,
                    textDecoration = TextDecoration.Underline,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            state.error?.let {
                Spacer(Modifier.height(8.dp))
                Text(
                    it,
                    color = MaterialTheme.colorScheme.error
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onSuccess = {}, onGoToSignup = {})
}