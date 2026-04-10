package com.example.edubound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import com.example.edubound.ui.theme.CyberGuardianTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CyberGuardianTheme {
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
                    Triple("Learn", "activity", Icons.Default.Build),
                    Triple("Me", "stats", Icons.Default.Person),
                    Triple("Set", "settings", Icons.Default.Settings)
                )

                items.forEach { (label, route, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = currentRoute == route,
                        onClick = { navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }}
                    )
                }
            }
        }
    ) { padding ->
        NavHost(navController, startDestination = "landing", Modifier.padding(padding)) {
            composable("landing") { LandingPage(onStart = { navController.navigate("activity") }) }
            composable("activity") { ActivityScreen() }
            composable("stats") { StatisticsScreen() }
            composable("settings") { SettingsScreen() }
        }
    }
}

// Landing Page
@Composable
fun LandingPage(onStart: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Top) {
        Text("Hello, Student!", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("What do you want to master today?", color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        Card(modifier = Modifier.fillMaxWidth().height(180.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
            Column(Modifier.padding(20.dp)) {
                Text("Featured Course", fontWeight = FontWeight.Bold)
                Text("Digital Citizenship: Protecting Your Privacy", fontSize = 20.sp)
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = onStart) { Text("Start Learning") }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Shortcuts", fontWeight = FontWeight.SemiBold)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Quick access button
            AssistChip(onClick = {}, label = { Text("Quizzes") }, leadingIcon = { Icon(Icons.Default.List, null) })
            AssistChip(onClick = {}, label = { Text("Achievements") }, leadingIcon = { Icon(Icons.Default.Star, null) })
        }
    }
}

// Activity Screen
@Composable
fun ActivityScreen() {
    var score by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Learning Simulation", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Topic: Spot the Privacy Risk")
        LinearProgressIndicator(progress = { 0.4f }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(32.dp))

        // Simulated interactive activities
        Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
            Column(Modifier.padding(16.dp)) {
                Text("A stranger asks for your school name online. What do you do?")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { score += 10 }, modifier = Modifier.fillMaxWidth()) { Text("Say 'No' and report") }
                OutlinedButton(onClick = { }, modifier = Modifier.fillMaxWidth()) { Text("Tell them, it's just a name") }
            }
        }
    }
}

// Statistics Screen
@Composable
fun StatisticsScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("My Growth", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        // Score Display
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("85", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text("Learning Score")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val progress = 3 // 假设完成了3个课程
                Text("$progress", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Text("Courses Done")
            }
        }
    }
}

// Settings Screen
@Composable
fun SettingsScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Preferences", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        ListItem(headlineContent = { Text("Sound Effects") }, trailingContent = { Switch(checked = true, onCheckedChange = {}) })
        ListItem(headlineContent = { Text("Difficulty Level") }, supportingContent = { Text("Middle School") })
    }
}