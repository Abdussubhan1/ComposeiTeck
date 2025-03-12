package com.example.itecktestingcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun appNavigation(onButtonClick: (String, NavHostController) -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash")

    {
        composable("splash") { splashScreen(navController) }
        composable("login") {
            loginScreen(
                LocalContext.current,
                onClick = {
                    onButtonClick(it, navController)
                }
            )
        }
        composable("mainscreen") { mainScreen() }
    }
}


