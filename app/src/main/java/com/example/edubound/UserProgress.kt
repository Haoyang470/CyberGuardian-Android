package com.example.edubound

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey val courseName: String,
    val isCompleted: Boolean = false,
    val completionTimestamp: Long = System.currentTimeMillis()
)