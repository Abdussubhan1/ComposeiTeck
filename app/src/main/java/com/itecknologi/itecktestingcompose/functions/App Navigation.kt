package com.itecknologi.itecktestingcompose.functions

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.itecknologi.itecktestingcompose.appScreens.MenuScreen
import com.itecknologi.itecktestingcompose.appScreens.RedoScreen
import com.itecknologi.itecktestingcompose.appScreens.RemovalScreen
import com.itecknologi.itecktestingcompose.appScreens.AllahHafiz
import com.itecknologi.itecktestingcompose.appScreens.DeviceEntryScreen
import com.itecknologi.itecktestingcompose.appScreens.FinalPicturesScreen
import com.itecknologi.itecktestingcompose.appScreens.LoginScreen
import com.itecknologi.itecktestingcompose.appScreens.NotificationScreen
import com.itecknologi.itecktestingcompose.appScreens.OTPScreen
import com.itecknologi.itecktestingcompose.appScreens.SplashScreen
import com.itecknologi.itecktestingcompose.appScreens.TestingPage
import com.itecknologi.itecktestingcompose.appScreens.initialPicTake
import com.itecknologi.itecktestingcompose.appPrefs.PreferenceManager
import com.itecknologi.itecktestingcompose.appScreens.JobAssigned
import com.itecknologi.itecktestingcompose.appScreens.finalTicket


@Composable
fun AppNavigation(context: Context, prefs: PreferenceManager) {

    val version = getAppVersion(context, prefs)
    Log.d("version of app", version)


    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash")

    {
        composable("splash") { SplashScreen(navController, version, prefs, context) }

        composable("login") {
            LoginScreen(
                context,
                navController,
                prefs
            )
        } //Login will pass the OTP to OTP Screen
        composable("OTP Screen/{code}") { backStackEntry ->
            val code = backStackEntry.arguments?.getString("code") ?: ""
            OTPScreen(context, navController, prefs, code)
        }

        composable("Menu Screen") { MenuScreen(context, navController, prefs) }
        composable("Assigned Tasks Screen") { JobAssigned(context,navController, prefs) }
        composable("NotificationScreen") { NotificationScreen(navController, prefs) }
        composable("mainscreen") { DeviceEntryScreen(context, navController, prefs) }
        composable("redo Screen") { RedoScreen(context, navController, prefs) }
        composable("removal Screen") { RemovalScreen(context, navController, prefs) }
        composable("initialPicturesScreen") { initialPicTake(context, navController, prefs) }
        composable("testingPage") { TestingPage(navController, context, prefs) }
        composable("finalPicturesScreen") { FinalPicturesScreen(navController, prefs, context) }
        composable("finalTicketScreen") { finalTicket(navController, prefs, context) }
        composable("AllahHafizScreen") { AllahHafiz(navController) }
    }
}


