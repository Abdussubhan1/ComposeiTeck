package com.example.itecktestingcompose.functions

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.itecktestingcompose.AppScreens.AllahHafiz
import com.example.itecktestingcompose.AppScreens.DeviceEntryScreen
import com.example.itecktestingcompose.AppScreens.FinalPicturesScreen
import com.example.itecktestingcompose.AppScreens.LoginScreen
import com.example.itecktestingcompose.AppScreens.NotificationScreen
import com.example.itecktestingcompose.AppScreens.OTPScreen
import com.example.itecktestingcompose.AppScreens.SplashScreen
import com.example.itecktestingcompose.AppScreens.TestingPage
import com.example.itecktestingcompose.AppScreens.initialPicTake


@Composable
fun AppNavigation(version: String,context: android.content.Context) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash")

    {
        composable("splash") { SplashScreen(navController,version) }
        composable("login") { LoginScreen(context, navController) }
        composable("OTP Screen") { OTPScreen(context, navController) }
        composable("NotificationScreen"){ NotificationScreen(navController) }
        composable("mainscreen") { DeviceEntryScreen(context, navController) }
        composable("initialPicturesScreen") { initialPicTake(context, navController) }
        composable("testingPage") { TestingPage(navController) }
        composable("finalPicturesScreen"){ FinalPicturesScreen(navController) }
        composable("AllahHafizScreen"){ AllahHafiz(navController) }
    }
}


