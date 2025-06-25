package com.itecknologi.itecktestingcompose.appScreens


import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.itecknologi.itecktestingcompose.apiFunctions.FCMUpdate
import com.itecknologi.itecktestingcompose.R
import com.itecknologi.itecktestingcompose.apiFunctions.checkLogin
import com.itecknologi.itecktestingcompose.appPrefs.PreferenceManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlin.system.exitProcess
import androidx.core.net.toUri
import com.itecknologi.itecktestingcompose.constants.Constants
import kotlinx.coroutines.withTimeoutOrNull


@Composable
fun SplashScreen(
    navController: NavHostController,
    version: String,
    prefs: PreferenceManager,
    context: Context
) {

    LaunchedEffect(Unit) {

        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults).await()

        //Doing so to prevent app crash while net not available
        try {
            withTimeoutOrNull(3000) {
                remoteConfig.fetch(0).await()
                remoteConfig.activate()
            }

        } catch (e: Exception) {
            Log.e("RemoteConfig", "Fetch failed: ${e.localizedMessage}")
            remoteConfig.activate()
        }
        val latestVersion = remoteConfig.getString("latest_app_version")

        Log.d("Latest Version", "Latest Version: $latestVersion")

        // Check if update is needed
        if (isUpdateRequired(current = version, latest = latestVersion)) {
            // Show update dialog before doing anything else
            showUpdateDialog(context)
            return@LaunchedEffect // Don't proceed to other navigation
        }


        delay(2000)

        if (prefs.getUserCNIC() != "") {
            val loginResponse = checkLogin(prefs.getAppLoginID(),prefs.getAppversion())

            if (loginResponse.success) {
                val check = FCMUpdate(prefs.getAppLoginID(), prefs.getFCM())

                if (check) {
                    Toast.makeText(context, loginResponse.message, Toast.LENGTH_SHORT).show()
                    navController.navigate("Menu Screen") {
                        popUpTo("splash") { inclusive = true }
                    }
                } else {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                    prefs.setUserCNIC("")
                }
            } else {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
//                prefs.setUserCNIC("")
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()
            }

        } else {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }


    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF122333)),
        contentAlignment = Alignment.Center,
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.gear_icon),
                contentDescription = "Gear Logo",
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White),
                modifier = Modifier
                    .width(99.dp)
                    .height(124.dp)
            )

            Text(
                text = "Testing",
                fontSize = 27.sp,
                fontWeight = FontWeight(700),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(0.7f)
            )
        }
        BottomLogo()
        Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
            Text(
                "\t\tVersion  $version",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 8.dp)
            )
        }
        Log.d("App version", "Version $version")
    }

}

fun isUpdateRequired(current: String, latest: String): Boolean {
    val curParts = current.split(".").map { it.toIntOrNull() ?: 0 }
    val latParts = latest.split(".").map { it.toIntOrNull() ?: 0 }

    for (i in 0 until maxOf(curParts.size, latParts.size)) {
        val cur = curParts.getOrElse(i) { 0 }
        val lat = latParts.getOrElse(i) { 0 }
        if (cur < lat) return true
        if (cur > lat) return false
    }
    return false
}

@SuppressLint("UseKtx")
fun showUpdateDialog(context: Context) {
    Handler(Looper.getMainLooper()).post {
        AlertDialog.Builder(context)
            .setTitle("Update Required")
            .setMessage("A new version of the app is available. Please update to continue.")
            .setCancelable(false)
            .setPositiveButton("Update") { _, _ ->
                try {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        "market://details?id=${context.packageName}".toUri()
                    )
                    context.startActivity(intent)
                } catch (e: Exception) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        "https://play.google.com/store/apps/details?id=${context.packageName}".toUri()
                    )
                    context.startActivity(intent)
                }

                if (context is Activity) {
                    context.finishAffinity()
                    exitProcess(0)
                }
            }
            .setNegativeButton("Cancel") { _, _ ->
                // Exit app on cancel
                if (context is Activity) {
                    context.finishAffinity()
                    exitProcess(0)
                }
            }
            .show()
    }
}


@Composable
fun BottomLogo() {

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Company Icon",
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White),
            modifier = Modifier.size(width = 23.dp, height = 21.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "By Itecknologi",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}


@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(
        navController = NavHostController(LocalContext.current),
        "",
        PreferenceManager(LocalContext.current),
        LocalContext.current
    )
}