package com.example.edubound

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Define course data structure
data class Subject(val name: String, val icon: String, val color: Color, val desc: String)

@Composable
fun LearnScreen(onNavigateToSubject: (String) -> Unit) {
    val subjects = listOf(
        Subject("Math", "📐", Color(0xFFE3F2FD), "Algebra, Geometry & Logic"),
        Subject("Physics", "⚛️", Color(0xFFF3E5F5), "Forces, Energy & Motion"),
        Subject("Chemistry", "🧪", Color(0xFFE8F5E9), "Atoms, Reactions & Elements")
    )

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("My Courses", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(subjects) { subject ->
                SubjectCard(subject) { onNavigateToSubject(subject.name) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectCard(subject: Subject, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = subject.color),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Text(subject.icon, fontSize = 40.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(subject.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(subject.desc, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}