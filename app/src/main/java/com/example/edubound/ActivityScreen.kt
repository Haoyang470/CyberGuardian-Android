package com.example.edubound

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActivityScreen(subjectName: String, onBack: () -> Unit) {
    val context = LocalContext.current

    // Make sure clicking on different subjects displays different questions.
    val isMath = subjectName.equals("Math", ignoreCase = true)
    val isPhysics = subjectName.equals("Physics", ignoreCase = true)
    val isChemistry = subjectName.equals("Chemistry", ignoreCase = true)

    val question = when {
        isMath -> "If the two legs of a right triangle are 3 and 4, what is the hypotenuse?"
        isPhysics -> "What is Newton's First Law also known as?"
        isChemistry -> "Which metal element is most abundant in the Earth's crust?"
        else -> "Ready to start today's challenge?"
    }

    val options = when {
        isMath -> listOf("5", "6", "7")
        isPhysics -> listOf("Inertia Law", "Acceleration Law", "Gravity")
        isChemistry -> listOf("Aluminum (Al)", "Iron (Fe)", "Oxygen (O)")
        else -> listOf("Start", "Skip", "View Notes")
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        IconButton(onClick = onBack) {
            // Use AutoMirrored to fix icon warnings
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
                            val isCorrect = answer == "5" || answer == "Inertia Law" || answer == "Aluminum (Al)"
                            if (isCorrect) {
                                globalScore += 50
                                Toast.makeText(context, "Correct! +50 Credits", Toast.LENGTH_SHORT).show()
                                onBack()
                            } else {
                                Toast.makeText(context, "Try again!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text(answer)
                    }
                }
            }
        }
    }
}