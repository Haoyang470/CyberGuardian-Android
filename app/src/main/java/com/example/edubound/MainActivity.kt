package com.example.edubound

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
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
    CourseModule("Math", "Geometry proof problems", "📐", Color(0xFFFFEBEE), 0.7f),
    CourseModule("Physics", "Fundamentals of Mechanics and Friction", "🍎", Color(0xFFE3F2FD), 0.3f),
    CourseModule("Chemistry", "Periodic table", "🧪", Color(0xFFE8F5E9), 0.1f),
    CourseModule("Art", "Different uses of color", "📚", Color(0xFFFFF3E0), 0.5f)
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
fun MainAppFrame() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val items = listOf(
                    Triple("Explore", "landing", Icons.Default.Search),
                    Triple("Learn", "activity/初中数学", Icons.Default.Edit),
                    Triple("Progress", "stats", Icons.Default.Person),
                    Triple("Settings", "settings", Icons.Default.Settings)
                )
                items.forEach { (label, route, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = currentRoute?.startsWith(route.split("/")[0]) == true,
                        onClick = {
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(navController, startDestination = "landing", Modifier.padding(padding)) {
            composable("landing") {
                LandingPage(onStart = { subject ->
                    navController.navigate("activity/$subject")
                })
            }
            composable("activity/{subjectName}") { backStackEntry ->
                val subject = backStackEntry.arguments?.getString("subjectName") ?: "初中数学"
                ActivityScreen(
                    subjectName = subject,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("stats") { StatisticsScreen() }
            composable("settings") { SettingsScreen() }
        }
    }
}

// Screen interface components

@Composable
fun LandingPage(onStart: (String) -> Unit) {
    val currentTip = remember { tipsLibrary.random() }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        contentPadding = PaddingValues(top = 48.dp, bottom = 24.dp)
    ) {
        item {
            Text("Hello, Student!", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
            Text("Empowering Your Future Learning", color = Color.Gray)
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(currentTip.icon, fontSize = 32.sp)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Daily Tip: ${currentTip.title}", fontWeight = FontWeight.Bold)
                        Text(currentTip.content, fontSize = 13.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            Text("Featured Courses", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(courseList) { course ->
                    CourseCard(course = course, onClick = { onStart(course.title) })
                }
            }
        }
    }
}

@Composable
fun ActivityScreen(subjectName: String, onBack: () -> Unit) {
    val context = LocalContext.current
    val question = when (subjectName) {
        "Math" -> "If the two legs of a right triangle are 3 and 4, what is the hypotenuse?"
        "Physics" -> "What is Newton's First Law also known as?"
        "Chemical" -> "What is the most abundant metallic element in the Earth's crust?"
        else -> "Ready to start today's challenge?"
    }
    val options = when (subjectName) {
        "Math" -> listOf("5", "6", "7")
        "Physics" -> listOf("Law of Inertia", "Law of Acceleration", "Gravity")
        "Chemical" -> listOf("Al", "Fe", "O")
        else -> listOf("Start", "Skip", "View Notes")
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(subjectName, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EEE6))
        ) {
            Column(Modifier.padding(24.dp)) {
                Text("Daily Challenge", color = Color(0xFF7D5822), fontWeight = FontWeight.Bold)
                Text(question, fontSize = 18.sp, modifier = Modifier.padding(vertical = 12.dp))

                options.forEach { answer ->
                    Button(
                        onClick = {
                            if (answer == "5" || answer == "Law of Inertia" || answer == "Al") {
                                globalScore += 50
                                if (subjectName == "Math") hasMathMedal = true
                                Toast.makeText(context, "Good", Toast.LENGTH_SHORT).show()
                                onBack()
                            } else {
                                Toast.makeText(context, "Check again", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7D5822))
                    ) {
                        Text(answer)
                    }
                }
            }
        }
    }
}

@Composable
fun StatisticsScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("My Growth", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(Modifier.padding(20.dp), Arrangement.Center) {
                Text("Total Credits")
                Text("$globalScore", fontSize = 42.sp, fontWeight = FontWeight.ExtraBold)
            }
        }

        Text("Medals Unlocked", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            MedalItem("📐", "Math", hasMathMedal)
            MedalItem("🧪", "Chem", false)
            MedalItem("🍎", "Phys", false)
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
fun SettingsScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Settings", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        ListItem(headlineContent = { Text("Study Reminders") }, trailingContent = { Switch(checked = true, onCheckedChange = {}) })
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