package com.example.edubound

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatisticsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "My Progress",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF7D5822)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- 1. 分数总览卡片 ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Total Points", fontSize = 14.sp, color = Color.Gray)

                Text(
                    text = "$globalScore",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF7D5822)
                )

                LinearProgressIndicator(
                    progress = { 0.65f },
                    modifier = Modifier.fillMaxWidth().height(8.dp).padding(vertical = 8.dp),
                    color = Color(0xFF7D5822),
                    trackColor = Color.White
                )
                Text("650 points to next level", fontSize = 12.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Learning Metrics
        Text("Learning Metrics", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MetricCard("Courses", "$coursesCompleted", Modifier.weight(1f))
            MetricCard("Streak", "$dailyStreak Days", Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Achievements
        Text("Achievements", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            MedalItem("📐", "Math Expert", hasMathMedal)
            MedalItem("⚛️", "Physics", hasPhysicsMedal)
            MedalItem("🧪", "Chemistry", hasChemistryMedal)
        }
    }
}

@Composable
fun MetricCard(title: String, value: String, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

@Composable
fun MedalItem(icon: String, name: String, isUnlocked: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = CircleShape,
            color = if (isUnlocked) Color(0xFFFFD700) else Color(0xFFF0F0F0),
            modifier = Modifier.size(64.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = icon,
                    fontSize = 30.sp,
                    modifier = Modifier.alpha(if (isUnlocked) 1f else 0.3f)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(name, fontSize = 12.sp, color = if (isUnlocked) Color.Black else Color.Gray)
    }
}