package com.example.cyberguardian

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CyberLearningDashboard()
                }
            }
        }
    }
}

@Composable
fun CyberLearningDashboard() {
    var selectedCourse by remember { mutableStateOf<String?>(null) }
    val isDeviceSecure = !isDeviceRooted() // 依然可以调用，因为它在同一个 package 下

    val courses = listOf(
        "Module 1: Mobile Security Basics",
        "Module 2: Identifying Root Risks",
        "Module 3: Secure Data Storage",
        "Module 4: Network Defense 101"
    )

    if (selectedCourse != null) {
        // 调用 CourseScreens.kt 里的函数
        CourseDetailScreen(courseName = selectedCourse!!) {
            selectedCourse = null
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = "CyberGuardian Academy",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 安全状态条
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = if (isDeviceSecure) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                shape = MaterialTheme.shapes.medium
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isDeviceSecure) "✅ Environment: Secure" else "⚠️ Warning: Root Detected",
                        color = if (isDeviceSecure) Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn {
                items(courses) { course ->
                    Card(
                        onClick = { selectedCourse = course },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("📖")
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = course)
                        }
                    }
                }
            }
        }
    }
}