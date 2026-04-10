package com.example.edubound

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LandingPage(
    onStart: (String) -> Unit,        // Handling course redirection (Learn)
    onNavigate: (String) -> Unit      // Handling page navigation (Progress/Settings)
) {
    // Network status management
    var networkQuote by remember { mutableStateOf("Fetching daily wisdom...") }
    var author by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    // Automatically retrieve famous internet quotes
    LaunchedEffect(Unit) {
        try {
            val response = NetworkModule.apiService.getRandomQuote()
            if (response.isNotEmpty()) {
                networkQuote = response[0].q
                author = response[0].a
            }
        } catch (e: Exception) {
            networkQuote = "Stay focused and keep learning every day!"
            author = "EduBound"
        } finally {
            isLoading = false
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentPadding = PaddingValues(top = 48.dp, bottom = 24.dp)
    ) {
        // Welcome Remarks
        item {
            Text(
                text = "Hello, Student!",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF7D5822)
            )
            Text(text = "Your learning dashboard", color = Color.Gray, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Internet Quote Cards
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFC8E6C9).copy(alpha = 0.6f))
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                    } else {
                        Text("💡", fontSize = 32.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Daily Inspiration", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = networkQuote, fontSize = 13.sp)
                        if (author.isNotEmpty()) Text("- $author", fontSize = 11.sp, color = Color.Gray)
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        // Quick Access
        item {
            Text("Quick Access", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Jump to progress page
                ActionCard(
                    title = "Progress",
                    icon = "📈",
                    color = Color(0xFFE3F2FD), // 淡蓝色
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate("stats") }
                )
                // Jump to settings page
                ActionCard(
                    title = "Settings",
                    icon = "⚙️",
                    color = Color(0xFFF3E5F5), // 淡紫色
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate("settings") }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        // Course List Title
        item {
            Text("Featured Courses", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Horizontal sliding course cards
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(courseList) { course ->
                    CourseCard(
                        course = course,
                        onClick = { onStart(course.title) }
                    )
                }
            }
        }
    }
}

// Homepage Quick Entry Component
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionCard(title: String, icon: String, color: Color, modifier: Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(icon, fontSize = 28.sp)
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.DarkGray)
        }
    }
}

data class Course(val title: String, val icon: String, val color: Color)

val courseList = listOf(
    Course("Math", "📐", Color(0xFFE3F2FD)),
    Course("Physics", "⚛️", Color(0xFFF3E5F5)),
    Course("Biology", "🌿", Color(0xFFE8F5E9))
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseCard(course: Course, onClick: () -> Unit) {
    Card(
        modifier = Modifier.size(width = 160.dp, height = 120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = course.color),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(course.icon, fontSize = 32.sp)
            Text(course.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}