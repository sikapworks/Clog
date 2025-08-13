package com.example.reposcribe.presentation.signup


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.reposcribe.presentation.components.AppTextField

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onSuccess: () -> Unit,
) {
    val state = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

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
            onClick = { viewModel.signUp()},
            modifier = Modifier.fillMaxWidth()
            ) {
            Text("Sign up")
        }

        //show error message if present
        state.error?.let{
            Spacer(Modifier.height(8.dp))
            Text(text = it.toString(), color = Color.Red)
        }

    }
}