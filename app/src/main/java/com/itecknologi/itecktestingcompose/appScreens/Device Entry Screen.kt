package com.itecknologi.itecktestingcompose.appScreens

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.itecknologi.itecktestingcompose.apiFunctions.DevValidationResult
import com.itecknologi.itecktestingcompose.apiFunctions.validateDev
import com.itecknologi.itecktestingcompose.constants.Constants
import com.itecknologi.itecktestingcompose.R
import kotlinx.coroutines.launch
import com.itecknologi.itecktestingcompose.appPrefs.PreferenceManager
import com.itecknologi.itecktestingcompose.functions.BottomLogo
import com.itecknologi.itecktestingcompose.functions.SelectedVehicle
import com.itecknologi.itecktestingcompose.functions.isInternetAvailable
import com.itecknologi.itecktestingcompose.functions.resetAllData
import kotlinx.coroutines.delay


@SuppressLint("UseKtx")
@Composable
fun DeviceEntryScreen(
    context: Context,
    navController: NavHostController,
    prefs: PreferenceManager
) {
    var showDialog by remember { mutableStateOf(false) }

    BackHandler {
        if (Constants.vehicleID != "") {
            showDialog = true
        } else {
            navController.popBackStack()
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Submission") },
            text = { Text("Are you sure you want to go back?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    Constants.vehicleID = ""
                    Constants.deviceID = ""
                    when(Constants.navigateBackto){
                        1->{navController.navigate("New Installations Assigned Tasks Screen") {
                            popUpTo("mainscreen") {
                                inclusive = true
                            }
                        }}
                        2->{navController.navigate("Redo Assigned Tasks Screen") {
                            popUpTo("mainscreen") {
                                inclusive = true
                            }
                        }}
                        3->{navController.navigate("Removal Assigned Tasks Screen") {
                            popUpTo("mainscreen") {
                                inclusive = true
                            }
                        }}
                    }
                    navController.navigate("New Installations Assigned Tasks Screen") {
                        popUpTo("mainscreen") {
                            inclusive = true
                        }
                    }
                }) {
                    Text("Go Back")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                }) {
                    Text("Cancel")
                }
            }
        )
    }

    val name = prefs.getTechnicianName()

    val hasNewNotification =
        remember { mutableStateOf(prefs.getHasNewNotification()) }

    var devID by remember { mutableStateOf("") }


    var validationResult by remember {
        mutableStateOf(
            DevValidationResult(
                ifDeviceExist = false,
                isLoading = false
            )
        )
    }

    val keyboard = LocalSoftwareKeyboardController.current
    val couroutineScope = rememberCoroutineScope()
    var testingStart by remember { mutableStateOf(false) }

    val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isLocationEnabled =
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF122333)) // Dark blue background
            .padding(horizontal = 16.dp),
//            .verticalScroll(rememberScrollState()),
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
            var isLoggingOut by remember { mutableStateOf(false) }
            val alpha by animateFloatAsState(
                targetValue = if (isLoggingOut) 0f else 1f,
                animationSpec = tween(durationMillis = 500),
                label = "logoutAnimation"
            )
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
                        }
                )
            }
            var confirmLogout by remember { mutableStateOf(false) }
            if (isLoggingOut) {
                AlertDialog(
                    onDismissRequest = { isLoggingOut = false },
                    title = { Text("Logout") },
                    text = { Text("Are you sure you want to Logout?") },
                    confirmButton = {
                        TextButton(onClick = {
                            isLoggingOut = false
                            confirmLogout = true
                            resetAllData()
                        }) {
                            Text("Logout")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            isLoggingOut = false
                        }) {
                            Text("Cancel")
                        }
                    }
                )
            }
            if (confirmLogout) {
                LaunchedEffect(true) {
                    delay(500) // Wait for animation to finish
                    prefs.setUserCNIC(cnic = "")
                    prefs.setTechnicianName(name = "")
                    prefs.setAppLoginID(id = "")
                    prefs.setTechnicianID(T_ID = 0)
                    Toast.makeText(context, "Logout Success", Toast.LENGTH_SHORT).show()
                    navController.navigate("login") {
                        popUpTo("mainscreen") { inclusive = true }
                    }
                }
            }

        }
        Spacer(modifier = Modifier.height(30.dp))

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
                            devID,
                            "Enter Device ID",
                            onValueChange = { devID = it },
                            true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                if ( devID.length >= 4) Color(
                                    0XFF39B54A
                                ) else Color.Gray
                            ) // Green search
                            .clickable(enabled = (devID.length >= 4)) {
                                keyboard?.hide()
                                validationResult = DevValidationResult(
                                    ifDeviceExist = false,
                                    isLoading = true
                                )
                                if (isLocationEnabled && isInternetAvailable(context)) {
                                    couroutineScope.launch {

                                        validationResult = validateDev(devID)

                                        if (validationResult.ifDeviceExist) {
                                            testingStart = true
                                            Constants.deviceID = devID

                                            Toast.makeText(
                                                context,
                                                "Device ID is valid",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            testingStart = false
                                            Toast.makeText(
                                                context,
                                                "Device Not found in Inventory",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                } else
                                    Toast.makeText(
                                        context,
                                        "No Network or Location",
                                        Toast.LENGTH_SHORT
                                    ).show()


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
                Spacer(modifier = Modifier.height(12.dp))
                SelectedVehicle()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Button
        Button(
            onClick = { navController.navigate("initialPicturesScreen") },
            enabled = testingStart,
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
                text = "Start Installation",
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        BottomLogo()


    }
}

@Composable
fun CustomTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        placeholder = {
            Text(
                placeholder,
                color = Color.DarkGray,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0XFF000000),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = keyboardOptions,
        singleLine = true
    )
}


@Preview
@Composable
fun MainScreenPreview() {
    DeviceEntryScreen(
        LocalContext.current,
        rememberNavController(),
        PreferenceManager(LocalContext.current)
    )
}

