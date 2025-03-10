package com.example.itecktestingcompose

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

val jameelNooriFont = FontFamily(Font(R.font.jameelnoori))

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            appNavigation()
        }
    }
}

fun getAppVersion(context: Context): String {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName ?: " "
    } catch (e: PackageManager.NameNotFoundException) {
        "N/A"
    }
}

@Composable
fun appNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash",)


    {
        composable("splash") { splashScreen(navController) }
        composable("login") { loginScreen(LocalContext.current,navController) }
        composable("mainscreen") { mainScreen() }
    }
}

@Composable
fun splashScreen(navController: NavHostController) {

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1000) // Wait for 3 seconds
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.gear_icon),
                contentDescription = "Gear Logo",
                modifier = Modifier
                    .size(100.dp)
            )

            Text(
                text = "Testing App",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF003366), // Dark blue color
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(0.7f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "Company Icon",
                modifier = Modifier.size(40.dp)
            )

            Text(
                text = "By iTecknologi",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF003366), // Dark blue color
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }


}


@Composable
fun loginScreen(context: Context, navController: NavHostController?) {

    val version = getAppVersion(context)
    var cnic by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Image(
            painter = painterResource(id = R.drawable.img_nic_2),
            contentDescription = "CNIC Image",
            modifier = Modifier
                .height(200.dp)
                .size(300.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        OutlinedTextField(
            value = cnic,
            onValueChange = { cnic = it.filter { char -> char.isDigit() || char == '-' } },
            label = {
                Text(
                    "اپنا شناختی کارڈ نمبر درج کریں",
                    textAlign = TextAlign.End,
                    fontFamily = jameelNooriFont, modifier = Modifier.fillMaxWidth(), fontSize = 26.sp
                )
            },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(32.dp)
        )

        Box(
            modifier = Modifier
                .width(200.dp)
                .height(42.dp)
                .padding(horizontal = 32.dp)
                .background(Color(0xFF008000), shape = RoundedCornerShape(10.dp))
                .clickable {
                    val cnicPattern = Regex("^\\d{5}-\\d{7}-\\d{1}$") // CNIC pattern

                    when {
                        cnic.isEmpty() -> {
                            Toast.makeText(context, "Please enter CNIC", Toast.LENGTH_SHORT).show()
                        }

                        !cnicPattern.matches(cnic) -> {
                            Toast.makeText(
                                context,
                                "Please enter a valid CNIC (XXXXX-XXXXXXX-X)",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {
                            Toast.makeText(
                                context,
                                "CNIC Submitted Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            if (navController != null) {
                                navController.navigate("mainscreen")
                            }
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.check_double_line),
                contentDescription = "Check Icon",
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "iTeck Icon",
                modifier = Modifier.size(40.dp)
            )

            Text(
                text = "By iTecknologi",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00008B)
            )

            Text(
                text = "Version: $version",
                fontSize = 12.sp,
                color = Color(0xFFA8A8A8)
            )
        }
    }

}


@Composable
fun mainScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize().padding(8.dp)
            .background(Color.White) // Adjust as per your design
    )
    {
        Column {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Profile Image
                    Image(
                        painter = painterResource(id = R.drawable.user_smile_fill),
                        contentDescription = "User Profile",
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                        //                        .background(Color.Green)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = "خوش آمدید",
                            fontFamily = jameelNooriFont,
                            textAlign = TextAlign.End,
                            fontSize = 22.sp,
                            color = Color.Gray,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = "Hamza",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Green
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))


                }
            }
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(26.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {}

        }


        }
    }


// I will use it in future
@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        contentAlignment = Alignment.Center // Centers content inside
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .padding(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(4.dp), // ProgressBar size
                    color = Color(0xFF00FF00) // Equivalent to `ui_green`
                )

                Image(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = "Loading Icon",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}



@Preview
@Composable
fun abc() {
    mainScreen()
}