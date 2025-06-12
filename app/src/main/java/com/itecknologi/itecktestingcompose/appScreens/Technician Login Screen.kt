package com.itecknologi.itecktestingcompose.appScreens

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.itecknologi.itecktestingcompose.apiFunctions.CNICValidationResult
import com.itecknologi.itecktestingcompose.apiFunctions.validateCnic
import com.itecknologi.itecktestingcompose.constants.Constants
import com.itecknologi.itecktestingcompose.functions.HandleDoubleBackToExit
import com.itecknologi.itecktestingcompose.R
import com.itecknologi.itecktestingcompose.appPrefs.PreferenceManager
import com.itecknologi.itecktestingcompose.functions.isInternetAvailable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(context: Context, navController: NavHostController, prefs: PreferenceManager) {

    HandleDoubleBackToExit()

    var validationResult by remember {
        mutableStateOf(
            CNICValidationResult(
                ifUserExist = false,
                isLoading = false,
                technicianName = ""
            )
        )
    }
    val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isLocationEnabled =
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    var cnic by remember { mutableStateOf("") }
    val couroutineScope = rememberCoroutineScope()
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF122333))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(130.dp))

        Image(
            painter = painterResource(id = R.drawable.img_nic_3),
            contentDescription = "CNIC Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(width = 356.dp, height = 150.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = cnic,
            onValueChange = { it -> cnic = it.filter { it.isDigit() } },
            placeholder = {
                Text(
                    "CNIC #",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 20.sp
                )
            },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0XFFD9D9D9),
                unfocusedContainerColor = Color(0XFFD9D9D9),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0XFF122333)
            ),
            shape = RoundedCornerShape(80.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .width(200.dp)
                .height(42.dp)
                .padding(horizontal = 32.dp)
                .background(
                    if (cnic == "" || cnic.length < 13 || cnic.length > 13) Color.Gray else Color(
                        0XFF39B54A
                    ), shape = RoundedCornerShape(17.dp)
                )
                .clickable(enabled = !validationResult.isLoading && cnic != "" && cnic.length == 13) {
                    keyboard?.hide() //hide the keyboard

                    //On submit, system will check if the location in ON or OFF and ask accordingly

                    if (isInternetAvailable(context)) {
                        if (isLocationEnabled) {
                            validationResult = CNICValidationResult(
                                ifUserExist = false,
                                isLoading = true,
                                technicianName = ""
                            )
                            Log.d("FCM token", "At the login time: ${prefs.getFCM()}")
                            couroutineScope.launch {
                                validationResult = validateCnic(
                                    cnic,
                                    Constants.mobileID,
                                    prefs.getFCM(),
                                    Constants.appVersion,
                                    Constants.osVersion,
                                    Constants.brand
                                )

                                if (validationResult.ifUserExist) {

                                    navController.navigate("OTP Screen")

                                    //Also saving in RAM
                                    Constants.cnic = cnic

                                } else {
                                    Toast.makeText(
                                        context,
                                        "Technician Not Registered",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                        } else Toast.makeText(context, "Location is OFF", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            context,
                            "No Network Connection",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (!validationResult.isLoading)
                Image(
                    painter = painterResource(id = R.drawable.check_double_line),
                    contentDescription = "Check Icon"
                )
            else
                Text("...", color = Color.Black, fontSize = 16.sp)
        }

        if (validationResult.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(25.dp)
                    .size(24.dp),
                color = Color(0XFF39B54A)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomLogo()
    }
}

@Preview
@Composable
fun LoginPreview() {
    LoginScreen(
        context = LocalContext.current,
        rememberNavController(),
        PreferenceManager(LocalContext.current)
    )
}


