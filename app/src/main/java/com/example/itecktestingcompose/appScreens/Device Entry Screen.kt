package com.itecknologi.itecktestingcompose.appScreens

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.itecknologi.itecktestingcompose.apiFunctions.DevValidationResult
import com.itecknologi.itecktestingcompose.apiFunctions.validateDev
import com.itecknologi.itecktestingcompose.constants.Constants
import com.itecknologi.itecktestingcompose.functions.HandleDoubleBackToExit
import com.itecknologi.itecktestingcompose.R
import kotlinx.coroutines.launch
import com.itecknologi.itecktestingcompose.apiFunctions.getVehicleDetails
import com.itecknologi.itecktestingcompose.modelClasses.VehData
import com.itecknologi.itecktestingcompose.appPrefs.PreferenceManager
import com.itecknologi.itecktestingcompose.functions.isInternetAvailable
import com.itecknologi.itecktestingcompose.functions.resetAllData
import com.itecknologi.itecktestingcompose.objects.vehicle_details
import kotlinx.coroutines.delay


@SuppressLint("UseKtx")
@Composable
fun DeviceEntryScreen(
    context: Context,
    navController: NavHostController,
    prefs: PreferenceManager
) {
    var showDialog by remember { mutableStateOf(false) }

    BackHandler {if (Constants.vehicleID != "") {
         showDialog = true
    } else {
        navController.popBackStack()
    } }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Submission") },
            text = { Text("Are you sure you want to go back?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    Constants.vehicleID = ""
                    navController.popBackStack()
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
    var vehicleEngineChassis by rememberSaveable { mutableStateOf("") }

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
    var isEnabled by remember { mutableStateOf(true) }
    var testingStart by remember { mutableStateOf(false) }
    var showVehicleCards by rememberSaveable { mutableStateOf(false) }
    var enableDeviceNumberEntry by remember { mutableStateOf(false) }
    var vehList by remember { mutableStateOf(emptyList<VehData>()) }
    var success by remember { mutableStateOf(false) }
    val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isLocationEnabled =
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)




    LaunchedEffect(Unit) {
        while (true) {
            success = getVehicleDetails(
                prefs.getAppLoginID(),
                prefs.getTechnicianID().toString()
            )
            Log.d("data", "DeviceEntryScreen: ${prefs.getTechnicianID()}")
            Log.d("data", "DeviceEntryScreen: ${prefs.getAppLoginID()}")
            if (success) {
                break
            } else {
                Toast.makeText(context, "Vehicle Details Not Updated!", Toast.LENGTH_SHORT).show()
            }
            delay(3000)
        }
    }


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
                            resetAllData()
                        }
                )
            }
            if (isLoggingOut) {
                LaunchedEffect(true) {
                    delay(500) // Wait for animation to finish
                    prefs.setUserCNIC(cnic = "")
                    prefs.setTechnicianName(name = "")
                    prefs.setAppLoginID(id = "")
                    prefs.setTechnicianID(T_ID = 0)

                    Toast.makeText(context, "Logout Success", Toast.LENGTH_SHORT).show()
                    navController.navigate("login") {
                        popUpTo("mainScreen") { inclusive = true }
                    }
                }
            }

        }
        Spacer(modifier = Modifier.height(40.dp))
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
                                    if (isLocationEnabled) {
                                        if (success) {
                                            vehList = searchInMemory(vehicleEngineChassis)
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
                                                "Something went wrong",
                                                Toast.LENGTH_SHORT
                                            ).show()
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
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        CustomTextField(
                            devID,
                            "Device Number",
                            onValueChange = { devID = it },
                            enableDeviceNumberEntry,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                if (enableDeviceNumberEntry && devID.length >= 4) Color(
                                    0XFF39B54A
                                ) else Color.Gray
                            ) // Green search
                            .clickable(enabled = (devID.length >= 4 && showVehicleCards)) {
                                keyboard?.hide()
                                validationResult = DevValidationResult(
                                    ifDeviceExist = false,
                                    isLoading = true
                                )
                                if (isLocationEnabled && isInternetAvailable(context)) {
                                    couroutineScope.launch {

                                        validationResult = validateDev(devID)

                                        if (validationResult.ifDeviceExist) {
                                            isEnabled = false
                                            testingStart = true
                                            Constants.deviceID = devID

                                            Toast.makeText(
                                                context,
                                                "Device ID is valid",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            isEnabled = true
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
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        if (showVehicleCards) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.70f)
                    .background(Color(0xFF122333), shape = RoundedCornerShape(24.dp))
                    .padding(10.dp)
            ) {
                VehicleListScreen(
                    vehicleList = vehList,
                    onSelectionChanged = { isSelected, vehicleID ->
                        enableDeviceNumberEntry = isSelected
                        Constants.vehicleID = vehicleID ?: ""
                    })
            }

        }

        Spacer(modifier = Modifier.height(12.dp))

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
                text = "TESTING START KARO",
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
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = Color.Gray
        ),
        keyboardOptions = keyboardOptions,
        singleLine = true
    )
}


@Composable
fun VehicleCard(
    vehicle: VehData,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF90A4AE) else Color(0xFF102027)
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFF90A4AE)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "${vehicle.MAKE} ${vehicle.MODEL}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Color: ${vehicle.COLOR}", color = Color.White)
                Text(text = "Engine: ${vehicle.ENGINE}", color = Color.White)
                Text(text = "Chassis: ${vehicle.CHASSIS}", color = Color.White)
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(16.dp)
                    .background(
                        color = if (isSelected) Color(0xFF39B54A) else Color.Transparent,
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = if (isSelected) Color.White else Color.Gray,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun VehicleListScreen(
    vehicleList: List<VehData>,
    onSelectionChanged: (Boolean, String?) -> Unit
) {
    var selectedVehicle by remember { mutableStateOf<VehData?>(null) }

    LazyColumn {
        items(vehicleList) { vehicle ->
            VehicleCard(
                vehicle = vehicle,
                isSelected = vehicle == selectedVehicle,
                onClick = {
                    val isSame =
                        vehicle == selectedVehicle // isSame is true if the same vehicle is selected
                    selectedVehicle = if (isSame) null else vehicle //toggle selection

                    onSelectionChanged(
                        !isSame,
                        if (!isSame) vehicle.V_ID else null
                    ) //Passes vehicle.V_ID if selected, or null if deselected.
                }
            )
        }
    }
}

fun searchInMemory(keyword: String): List<VehData> {
    return vehicle_details.dataList.filter { data ->
        data.CHASSIS.contains(keyword, ignoreCase = true) ||
                data.ENGINE.contains(keyword, ignoreCase = true)
    } //this will match the entered keyword and provide results accordingly in List form
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

