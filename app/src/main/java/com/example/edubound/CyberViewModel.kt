package com.example.edubound

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CyberViewModel(application: Application) : AndroidViewModel(application) {

    // 1. 课程数据列表
    val courses = listOf(
        "Module 1: Reconnaissance",
        "Module 2: Firewall Config",
        "Module 3: Secure Data Storage",
        "Module 4: Network Defense 101"
    )

    // 2. 修复 Unresolved reference: completedCourses
    private val _completedCourses = MutableStateFlow<List<String>>(emptyList())
    val completedCourses: StateFlow<List<String>> = _completedCourses.asStateFlow()

    // 3. 进度数据
    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    fun markCourseCompleted(courseName: String) {
        val currentList = _completedCourses.value.toMutableList()
        if (!currentList.contains(courseName)) {
            currentList.add(courseName)
            _completedCourses.value = currentList
            // 更新进度百分比
            _progress.value = currentList.size.toFloat() / courses.size.toFloat()
        }
    }
}