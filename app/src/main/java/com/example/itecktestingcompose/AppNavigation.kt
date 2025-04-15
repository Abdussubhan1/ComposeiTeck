package com.example.itecktestingcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash")

    {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(LocalContext.current, navController) }
        composable("mainscreen") { DeviceEntryScreen(LocalContext.current, navController) }
        composable("testingPage") { TestingPage(navController) }
    }
}


