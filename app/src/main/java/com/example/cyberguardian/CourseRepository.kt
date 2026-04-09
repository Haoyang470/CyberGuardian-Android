package com.example.cyberguardian

import kotlinx.coroutines.flow.Flow

class CourseRepository(private val progressDao: ProgressDao) {
    val allProgress: Flow<List<UserProgress>> = progressDao.getAllProgress()

    suspend fun markAsComplete(courseName: String) {
        progressDao.insertProgress(UserProgress(courseName = courseName, isCompleted = true))
    }
}