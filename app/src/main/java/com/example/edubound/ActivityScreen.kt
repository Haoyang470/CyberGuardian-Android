package com.example.edubound

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(subjectName: String, onBack: () -> Unit) {
    val context = LocalContext.current

    val (question, options, correctAnswer) = when (subjectName) {
        "Math" -> if (gameDifficulty == "Normal") {
            Triple("What is 15 * 4?", listOf("50", "60", "70", "80"), "60")
        } else {
            Triple("Solve for x: 2x + 15 = 45", listOf("10", "15", "20", "25"), "15")
        }

        "Physics" -> if (gameDifficulty == "Normal") {
            Triple("Unit of Force?", listOf("Joule", "Watt", "Newton", "Pascal"), "Newton")
        } else {
            Triple("Force = Mass × ?", listOf("Velocity", "Time", "Acceleration", "Density"), "Acceleration")
        }

        "Chemistry" -> if (gameDifficulty == "Normal") {
            Triple("Symbol for Gold?", listOf("Ag", "Au", "Fe", "Cu"), "Au")
        } else {
            Triple("What is the atomic number of Oxygen?", listOf("6", "7", "8", "9"), "8")
        }

        else -> Triple("Ready?", listOf("Yes", "No"), "Yes")
    }

    // Status management: Selected answer
    var selectedOption by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$subjectName Quiz", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            // Question text
            Text(
                text = question,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 30.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Option list
            Column(
                modifier = Modifier
                    .selectableGroup()
                    .weight(1f)
            ) {
                options.forEach { text ->
                    val isSelected = (selectedOption == text)
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .selectable(
                                selected = isSelected,
                                onClick = { selectedOption = text },
                                role = Role.RadioButton
                            ),
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) Color(0xFFE8F5E9) else Color(0xFFF5F5F5),
                        border = if (isSelected) ButtonDefaults.outlinedButtonBorder else null
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = null // 已经在 Surface 的 selectable 中处理了
                            )
                            Text(
                                text = text,
                                modifier = Modifier.padding(start = 16.dp),
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            // Submit button
            Button(
                onClick = {
                    if (selectedOption == correctAnswer) {
                        // Bonus Points Logic
                        globalScore += 100
                        coursesCompleted += 1

                        // Light up the corresponding badge
                        when (subjectName) {
                            "Math" -> hasMathMedal = true
                            "Physics" -> hasPhysicsMedal = true
                            "Chemical" -> hasChemistryMedal = true
                        }

                        // Persistently save to phone storage
                        MainActivity.saveProgress(context)

                        Toast.makeText(context, "Correct! +100 Points", Toast.LENGTH_SHORT).show()
                        onBack() // Return to the course list after answering correctly.
                    } else {
                        Toast.makeText(context, "Incorrect, please try again!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = selectedOption.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Submit Answer", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}