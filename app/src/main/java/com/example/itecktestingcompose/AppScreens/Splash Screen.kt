package com.example.itecktestingcompose.AppScreens



import android.util.Log
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.itecktestingcompose.APIFunctions.FCMUpdate
import com.example.itecktestingcompose.R
import com.example.itecktestingcompose.appPrefs.PreferenceManager


@Composable
fun SplashScreen(
    navController: NavHostController,
    version: String,
    prefs: PreferenceManager
) {

    LaunchedEffect(Unit) {

        kotlinx.coroutines.delay(2000) // Wait for 2 seconds
        if (prefs.getUserCNIC() != "") {
            Log.d("FCM token", "FCM submitted at splash ${prefs.getFCM()}")
            val check = FCMUpdate(prefs.getAppLoginID(), prefs.getFCM())
            Log.d("FCM at Splash", "SplashScreen: $check")
            if (check) {
                navController.navigate("mainscreen") {
                    popUpTo("splash") { inclusive = true }
                }
            } else {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
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
                "\t\tApp Version  $version",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 8.dp)
            )
        }
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
