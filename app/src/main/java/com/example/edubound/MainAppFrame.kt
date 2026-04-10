package com.example.edubound

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*

@Composable
fun MainAppFrame() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                // Explore
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Explore") },
                    selected = currentDestination?.hierarchy?.any { it.route == "landing" } == true,
                    onClick = {
                        navController.navigate("landing") {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                // Learn
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Edit, contentDescription = null) },
                    label = { Text("Learn") },
                    selected = currentDestination?.hierarchy?.any { it.route?.startsWith("activity") == true } == true,
                    onClick = {
                        navController.navigate("activity/Math") {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                // Progress
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = null) },
                    label = { Text("Progress") },
                    selected = currentDestination?.hierarchy?.any { it.route == "stats" } == true,
                    onClick = {
                        navController.navigate("stats") {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                // Settings
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    label = { Text("Settings") },
                    selected = currentDestination?.hierarchy?.any { it.route == "settings" } == true,
                    onClick = {
                        navController.navigate("settings") {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "landing",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("landing") {
                LandingPage(onStart = { subject ->
                    navController.navigate("activity/$subject")
                })
            }
            composable("activity/{subjectName}") { backStackEntry ->
                val subject = backStackEntry.arguments?.getString("subjectName") ?: "Math"
                ActivityScreen(
                    subjectName = subject,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("stats") { StatisticsScreen() }
            composable("settings") { SettingsScreen() }
        }
    }
}