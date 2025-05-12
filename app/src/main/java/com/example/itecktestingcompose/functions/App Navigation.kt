package com.example.itecktestingcompose.functions

import android.content.Context
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
import com.example.itecktestingcompose.appPrefs.PreferenceManager


@Composable
fun AppNavigation( context: Context, prefs: PreferenceManager) {

    val version = getAppVersion(context)

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash")

    {
        composable("splash") { SplashScreen(navController, version, prefs) }
        composable("login") { LoginScreen(context, navController, prefs) }
        composable("OTP Screen") { OTPScreen(context, navController, prefs) }
        composable("NotificationScreen") { NotificationScreen(navController) }
        composable("mainscreen") { DeviceEntryScreen(context, navController, prefs) }
        composable("initialPicturesScreen") { initialPicTake(context, navController, prefs) }
        composable("testingPage") { TestingPage(navController, context, prefs) }
        composable("finalPicturesScreen") { FinalPicturesScreen(navController, prefs, context) }
        composable("AllahHafizScreen") { AllahHafiz(navController) }
    }
}


