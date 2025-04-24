package com.example.itecktestingcompose.Functions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.itecktestingcompose.AppScreens.AllahHafiz
import com.example.itecktestingcompose.AppScreens.DeviceEntryScreen
import com.example.itecktestingcompose.AppScreens.FinalPicturesScreen
import com.example.itecktestingcompose.AppScreens.LoginScreen
import com.example.itecktestingcompose.AppScreens.SplashScreen
import com.example.itecktestingcompose.AppScreens.TestingPage


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash")

    {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(LocalContext.current, navController) }
        composable("mainscreen") { DeviceEntryScreen(LocalContext.current, navController) }
        composable("testingPage") { TestingPage(navController) }
        composable("finalPicturesScreen"){ FinalPicturesScreen(navController) }
        composable("AllahHafizScreen"){ AllahHafiz(navController) }

    }
}


