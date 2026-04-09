package com.example.cyberguardian

import android.app.Application
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// 导航路由定义
sealed class Screen(val route: String) {
    object Landing : Screen("landing")
    object Activity : Screen("activity/{courseName}")
    object Settings : Screen("settings")
    object Stats : Screen("stats")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 安全设计：禁止截屏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // 使用 Factory 初始化 ViewModel，这是架构设计的核心
                    val cyberViewModel: CyberViewModel = viewModel(
                        factory = viewModelFactory {
                            initializer {
                                // 修复：使用正确的上下文获取 Application
                                val app = this[APPLICATION_KEY] as Application
                                CyberViewModel(app)
                            }
                        }
                    )

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Landing.route
                    ) {
                        composable(Screen.Landing.route) {
                            CyberLearningDashboard(
                                navViewModel = cyberViewModel,
                                onNavigateToCourse = { courseName ->
                                    navController.navigate("activity/$courseName")
                                },
                                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                                onNavigateToStats = { navController.navigate(Screen.Stats.route) }
                            )
                        }

                        composable(
                            route = Screen.Activity.route,
                            arguments = listOf(navArgument("courseName") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val courseName = backStackEntry.arguments?.getString("courseName") ?: ""
                            CourseDetailScreen(courseName = courseName) {
                                // 更新进度并返回
                                cyberViewModel.markCourseCompleted(courseName)
                                navController.popBackStack()
                            }
                        }

                        composable(Screen.Settings.route) {
                            SettingsScreen { navController.popBackStack() }
                        }

                        composable(Screen.Stats.route) {
                            UserStatsScreen { navController.popBackStack() }
                        }
                    } // NavHost End
                } // Surface End
            } // MaterialTheme End
        } // setContent End
    }
}

@Composable
fun CyberLearningDashboard(
    navViewModel: CyberViewModel,
    onNavigateToCourse: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToStats: () -> Unit
) {
    // 观察来自 Room 数据库的数据流
    val completedCourses by navViewModel.completedCourses.collectAsState(initial = emptyList())
    val progress by navViewModel.progress.collectAsState(initial = 0f)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "CyberGuardian Academy",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = onNavigateToStats, modifier = Modifier.weight(1f)) {
                Text("📊 Stats")
            }
            Button(onClick = onNavigateToSettings, modifier = Modifier.weight(1f)) {
                Text("⚙️ Settings")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 进度展示
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Overall Progress: ${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(navViewModel.courses) { course ->
                Card(
                    onClick = { onNavigateToCourse(course) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (completedCourses.contains(course))
                            Color(0xFFE8F5E9) else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(if (completedCourses.contains(course)) "✅" else "📖")
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = course, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}