package com.example.itecktestingcompose.functions

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.itecktestingcompose.appScreens.AllahHafiz
import com.example.itecktestingcompose.appScreens.DeviceEntryScreen
import com.example.itecktestingcompose.appScreens.FinalPicturesScreen
import com.example.itecktestingcompose.appScreens.LoginScreen
import com.example.itecktestingcompose.appScreens.NotificationScreen
import com.example.itecktestingcompose.appScreens.OTPScreen
import com.example.itecktestingcompose.appScreens.SplashScreen
import com.example.itecktestingcompose.appScreens.TestingPage
import com.example.itecktestingcompose.appScreens.initialPicTake
import com.example.itecktestingcompose.appPrefs.PreferenceManager


@Composable
fun AppNavigation( context: Context, prefs: PreferenceManager) {

    val version = getAppVersion(context)

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash")

    {
        composable("splash") { SplashScreen(navController, version, prefs,context) }
        composable("login") { LoginScreen(context, navController, prefs) }
        composable("OTP Screen") { OTPScreen(context, navController, prefs) }
        composable("NotificationScreen") { NotificationScreen(navController,prefs) }
        composable("mainscreen") { DeviceEntryScreen(context, navController, prefs) }
        composable("initialPicturesScreen") { initialPicTake(context, navController, prefs) }
        composable("testingPage") { TestingPage(navController, context, prefs) }
        composable("finalPicturesScreen") { FinalPicturesScreen(navController, prefs, context) }
        composable("AllahHafizScreen") { AllahHafiz(navController) }
    }
}


