package com.example.edubound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edubound.ui.theme.EduBoundTheme

// Global State and Data Model

var globalScore by mutableIntStateOf(1250)
var hasMathMedal by mutableStateOf(false)

data class DailyTip(val title: String, val content: String, val icon: String)

data class CourseModule(
    val title: String,
    val description: String,
    val icon: String,
    val color: Color,
    val progress: Float
)

val tipsLibrary = listOf(
    DailyTip("Eye health", "Use your eyes for 20 minutes, then look at something 20 feet away for 20 seconds.", "👀"),
    DailyTip("Protect privacy", "Do not disclose your personal information casually.", "🛡️"),
    DailyTip("Learning efficiency", "Study for 25 minutes, then rest for 5 minutes for higher efficiency.", "⏱️"),
    DailyTip("Online social networking", "Be friendly to people online.", "📍"),
    DailyTip("Emotional Management", "When feeling stressed, try adjusting your mindset.", "🌈")
)

val courseList = listOf(
    CourseModule("Math", "Geometry: Finding auxiliary lines.", "📐", Color(0xFFFFEBEE), 0.7f),
    CourseModule("Physics", "Mechanics: Newton's Laws.", "🍎", Color(0xFFE3F2FD), 0.3f),
    CourseModule("Chemistry", "Periodic Table & Reactions.", "🧪", Color(0xFFE8F5E9), 0.1f)
)

// Main Activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EduBoundTheme {
                MainAppFrame()
            }
        }
    }
}
@Composable
fun MedalItem(icon: String, name: String, isUnlocked: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = CircleShape,
            color = if (isUnlocked) Color(0xFFFFD700) else Color.LightGray,
            modifier = Modifier.size(60.dp)
        ) {
            Box(contentAlignment = Alignment.Center) { Text(icon, fontSize = 28.sp) }
        }
        Text(name, fontSize = 12.sp)
    }
}

@Composable
fun CourseCard(course: CourseModule, onClick: () -> Unit) {
    Card(
        modifier = Modifier.width(200.dp).height(175.dp).clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = course.color),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(Modifier.padding(16.dp).fillMaxSize()) {
            Text(course.icon, fontSize = 28.sp)
            Text(course.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(course.description, fontSize = 11.sp, color = Color.DarkGray, maxLines = 1)
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Progress", fontSize = 10.sp, color = Color.Gray)
                    Text("${(course.progress * 100).toInt()}%", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                LinearProgressIndicator(
                    progress = { course.progress },
                    modifier = Modifier.fillMaxWidth().height(6.dp),
                    strokeCap = StrokeCap.Round
                )
            }
        }
    }
}