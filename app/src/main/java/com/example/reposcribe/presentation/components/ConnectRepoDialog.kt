package com.example.reposcribe.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.reposcribe.domain.model.Repo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectRepoDialog(
    repos: List<Repo>,
    onDismiss: () -> Unit,
    onConfirm: (Repo) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedRepo by rememberSaveable { mutableStateOf<Repo?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Repository") },
        text = {
            Column {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedRepo?.name ?: "",
                        onValueChange = {},
                        label = { Text("Repository") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .clickable { expanded = true }
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        repos.forEach { repo ->
                            DropdownMenuItem(
                                text = { Text(repo.name) },
                                onClick = {
                                    selectedRepo = repo
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                enabled = selectedRepo != null,
                onClick = {
                    selectedRepo?.let { onConfirm(it) }
                }
            ) { Text("Connect") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
