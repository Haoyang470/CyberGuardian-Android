package com.example.edubound

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.FilterChip

@Composable
fun SettingsScreen() {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Settings", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        Text("Select Difficulty", fontSize = 18.sp, fontWeight = FontWeight.Medium)

        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
            FilterChip(
                selected = gameDifficulty == "Normal",
                onClick = {
                    gameDifficulty = "Normal"
                    MainActivity.saveProgress(context)
                },
                label = { Text("Normal") },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )

            FilterChip(
                selected = gameDifficulty == "Hard",
                onClick = {
                    gameDifficulty = "Hard"
                    MainActivity.saveProgress(context)
                },
                label = { Text("Hard") },
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )
        }

        Text(
            text = "Current Mode: $gameDifficulty",
            color = if(gameDifficulty == "Hard") Color.Red else Color.DarkGray,
            fontSize = 14.sp
        )
    }
}