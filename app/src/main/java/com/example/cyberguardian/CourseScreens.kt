package com.example.cyberguardian

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CourseDetailScreen(courseName: String, onBack: () -> Unit) {
    val isSecure = !isDeviceRooted()

    var showAttackDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("⬅ Back to Dashboard")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = courseName, style = MaterialTheme.typography.headlineMedium)

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        if (isSecure) {
            val detailContent = when {
                courseName.contains("Module 1") -> "Key point: Android application sandbox mechanism isolates processes through UID."
                courseName.contains("Module 2") -> "Deep risk: Rooting devices can violate the system's SELinux policy."
                courseName.contains("Module 3") -> "Practice: It is recommended to use the Android Keystore system to store private keys."
                else -> "Network Defense: Introduction to preventing MITM attacks and SSL Pinning technology"
            }

            Text(text = detailContent, style = MaterialTheme.typography.bodyLarge)
        } else {
            Surface(
                color = Color(0xFFFFEBEE),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "🔒 Security Alert: Device is rooted, content is locked.",
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFFC62828),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

    if (showAttackDialog) {
        AlertDialog(
            onDismissRequest = { showAttackDialog = false },
            confirmButton = {
                Button(onClick = { showAttackDialog = false }) {
                    Text("I understand the risks.")
                }
            },
            title = {
                Text("🔴 Simulated hacking attack successful", color = Color(0xFFC62828))
            },
            text = {
                Text("Warning: Root access allows apps to steal your private data. This is a major security risk for banking and education apps.")
            }
        )
    }
}


