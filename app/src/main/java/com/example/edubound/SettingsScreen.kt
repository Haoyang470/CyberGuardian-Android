package com.example.edubound

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen() {
    // Use remember to save user preference state
    var isSoundEnabled by remember { mutableStateOf(true) }
    var difficultyLevel by remember { mutableFloatStateOf(1f) } // 0: 简单, 1: 中等, 2: 困难

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Settings",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Sound Settings
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            ListItem(
                headlineContent = { Text("Sound Effects", fontWeight = FontWeight.Medium) },
                supportingContent = { Text("Enable sounds during practice") },
                trailingContent = {
                    Switch(
                        checked = isSoundEnabled,
                        onCheckedChange = { isSoundEnabled = it }
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Difficulty Settings
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Practice Difficulty", fontWeight = FontWeight.Medium)
                Text(
                    text = when(difficultyLevel.toInt()) {
                        0 -> "Easy (Junior High)"
                        1 -> "Medium (Senior High)"
                        else -> "Hard (Competition)"
                    },
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Slider(
                    value = difficultyLevel,
                    onValueChange = { difficultyLevel = it },
                    valueRange = 0f..2f,
                    steps = 1 // 只有 0, 1, 2 三档
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "EduBound v1.2.0",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 12.sp,
            color = androidx.compose.ui.graphics.Color.Gray
        )
    }
}