package com.example.itecktestingcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun appNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash")

    {
        composable("splash") { splashScreen(navController) }
        composable("login") { loginScreen(LocalContext.current, navController) }
        composable("mainscreen") { mainScreen(LocalContext.current, navController) }
        composable("historyscreen") { showHistory(LocalContext.current) }
    }
}