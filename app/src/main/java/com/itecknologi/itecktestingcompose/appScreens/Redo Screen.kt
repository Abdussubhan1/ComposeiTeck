package com.itecknologi.itecktestingcompose.appScreens


import android.content.Context
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.itecknologi.itecktestingcompose.R
import com.itecknologi.itecktestingcompose.apiFunctions.DevValidationResult
import com.itecknologi.itecktestingcompose.appPrefs.PreferenceManager
import com.itecknologi.itecktestingcompose.constants.Constants
import com.itecknologi.itecktestingcompose.functions.BottomLogo
import com.itecknologi.itecktestingcompose.functions.VehicleListScreen
import com.itecknologi.itecktestingcompose.functions.isInternetAvailable
import com.itecknologi.itecktestingcompose.functions.resetAllData
import com.itecknologi.itecktestingcompose.modelClasses.Data
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun RedoScreen(context: Context, navController: NavHostController, prefs: PreferenceManager) {
    val name = prefs.getTechnicianName()
    var isLoggingOut by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isLoggingOut) 0f else 1f,
        animationSpec = tween(durationMillis = 500),
        label = "logoutAnimation"
    )
    val hasNewNotification =
        remember { mutableStateOf(prefs.getHasNewNotification()) }
    var vehicleEngineChassis by rememberSaveable { mutableStateOf("") }
    val keyboard = LocalSoftwareKeyboardController.current

//    val locationManager =
//        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//    val isLocationEnabled =
//        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
//                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    var vehList by remember { mutableStateOf(emptyList<Data>()) }
    var devID by remember { mutableStateOf("") }
    var showVehicleCards by rememberSaveable { mutableStateOf(false) }
    var enableStartRedo by remember { mutableStateOf(false) }
    var validationResult by remember {
        mutableStateOf(
            DevValidationResult(
                ifDeviceExist = false,
                isLoading = false
            )
        )
    }
    val couroutineScope = rememberCoroutineScope()





    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF122333))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.user_smile_fill), // Add smile icon
                    contentDescription = null,
                    tint = Color(0XFF39B54A),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Khush Amdeed!", color = Color.White, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = name,
                        color = Color(0XFF39B54A),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(.6f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Tips",
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                navController.navigate("NotificationScreen")
                                prefs.setHasNewNotification(value = false)
                                hasNewNotification.value = false
                            }
                    )

                    if (hasNewNotification.value) {
                        Box(
                            modifier = Modifier
                                .size(8.dp) // Small dot
                                .background(Color(0xFFFFEB3B), shape = CircleShape)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        imageVector = Icons.Default.PowerSettingsNew,
                        contentDescription = "Logout",
                        tint = Color.Red,
                        modifier = Modifier
                            .size(32.dp)
                            .alpha(alpha)
                            .clickable {
                                isLoggingOut = true
                                resetAllData()
                            }
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text("Redo", color = Color.White, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(20.dp))

        if (isLoggingOut) {
            LaunchedEffect(true) {
                delay(500) // Wait for animation to finish
                prefs.setUserCNIC(cnic = "")
                prefs.setTechnicianName(name = "")
                prefs.setAppLoginID(id = "")
                prefs.setTechnicianID(T_ID=0)
                Toast.makeText(context, "Logout Success", Toast.LENGTH_SHORT).show()
                navController.navigate("login") {
                    popUpTo("redo Screen") { inclusive = true }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0XFF182b3c), shape = RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {

                        CustomTextField(
                            vehicleEngineChassis,
                            "Engine/Chassis",
                            onValueChange = { vehicleEngineChassis = it },
                            true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )

                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                if (vehicleEngineChassis.length >= 3) Color(
                                    0XFF39B54A
                                ) else Color.Gray
                            ) // Green search
                            .clickable(enabled = vehicleEngineChassis.length >= 3) {
                                keyboard?.hide()

                                if (isInternetAvailable(context)) {
                                    if (true) {
                                            // todo Api Call for the only vehicle and get the result in vehList variable
                                            if (vehList.isNotEmpty()) {
                                                showVehicleCards = true
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Vehicle Not Found",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                showVehicleCards = false
                                            }

                                    } else {
                                        showVehicleCards = false
                                        Toast.makeText(
                                            context,
                                            "Location is OFF",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                } else {
                                    showVehicleCards = false
                                    Toast.makeText(
                                        context,
                                        "No Internet Connection",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                }

            }
        }
        Spacer(modifier = Modifier.height(12.dp))

//        if (showVehicleCards) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(0.70f)
//                    .background(Color(0xFF122333), shape = RoundedCornerShape(24.dp))
//                    .padding(10.dp)
//            ) {
//                VehicleListScreen(
//                    vehicleList = vehList,
//                    onSelectionChanged = { isSelected, vehicleID ->
//                        enableStartRedo = isSelected
//                        Constants.vehicleID = vehicleID ?: ""
//                    })
//            }
//
//        }
        Spacer(modifier = Modifier.height(12.dp))

        // Button For Starting Redo Process

        Button(
            onClick = { navController.navigate("initialPicturesScreen") },
            enabled = enableStartRedo,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0XFF39B54A), // Green color
                contentColor = Color.White,
                disabledContainerColor = Color(0XFF39B54A).copy(alpha = 0.05f),
                disabledContentColor = Color.White.copy(alpha = 0.3f)
            ),
            contentPadding = PaddingValues(0.dp) // Ensures same text alignment as Box
        ) {
            Text(
                text = "Start Redo",
                fontWeight = FontWeight.SemiBold
            )
        }
//        Spacer(modifier = Modifier.weight(1f))

        BottomLogo()


    }
}

@Preview
@Composable
fun RedoPreview() {
    RedoScreen(
        context = LocalContext.current,
        rememberNavController(), PreferenceManager(LocalContext.current)
    )
}
