package com.example.edubound

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme

// Global state
var globalScore by mutableIntStateOf(0)
var coursesCompleted by mutableIntStateOf(0)
var dailyStreak by mutableIntStateOf(0)
var hasMathMedal by mutableStateOf(false)
var hasPhysicsMedal by mutableStateOf(false)
var hasChemistryMedal by mutableStateOf(false)

var gameDifficulty by mutableStateOf("Normal")

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load progress from local storage at startup
        loadProgressFromStorage()

        setContent {
            MaterialTheme {
                MainAppFrame()
            }
        }
    }

    /**
     * Reading data from SharedPreferences
     */
    private fun loadProgressFromStorage() {
        val prefs = getSharedPreferences("EduBoundPrefs", Context.MODE_PRIVATE)

        gameDifficulty = prefs.getString("game_difficulty", "Normal") ?: "Normal"
        globalScore = prefs.getInt("total_score", 0)
        coursesCompleted = prefs.getInt("completed_courses", 0)
        dailyStreak = prefs.getInt("daily_streak", 0)
        hasMathMedal = prefs.getBoolean("medal_math", false)
    }

    /**
     * Convenient for direct calls from ActivityScreen or elsewhere
     */
    companion object {
        fun saveProgress(context: Context) {
            val prefs = context.getSharedPreferences("EduBoundPrefs", Context.MODE_PRIVATE)
            val editor = prefs.edit()

            editor.putString("game_difficulty", gameDifficulty)
            editor.putInt("total_score", globalScore)
            editor.putInt("completed_courses", coursesCompleted)
            editor.putInt("daily_streak", dailyStreak)
            editor.putBoolean("medal_math", hasMathMedal)

            editor.apply()
        }
    }
}