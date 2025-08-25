package com.itecknologi.itecktestingcompose.appScreens


import android.content.Context
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.itecknologi.itecktestingcompose.apiFunctions.ValidateLocationResponse
import com.itecknologi.itecktestingcompose.apiFunctions.cmdQueueCheck
import com.itecknologi.itecktestingcompose.apiFunctions.RelayResponse
import com.itecknologi.itecktestingcompose.apiFunctions.setRelayStatus
import com.itecknologi.itecktestingcompose.apiFunctions.validateBattery
import com.itecknologi.itecktestingcompose.apiFunctions.validateIgnition
import com.itecknologi.itecktestingcompose.apiFunctions.validateLoc
import com.itecknologi.itecktestingcompose.constants.Constants
import com.itecknologi.itecktestingcompose.functions.HandleDoubleBackToExit
import com.itecknologi.itecktestingcompose.functions.checkLocationWithinRange
import com.itecknologi.itecktestingcompose.R
import com.itecknologi.itecktestingcompose.apiFunctions.BatteryResponse
import com.itecknologi.itecktestingcompose.apiFunctions.IgnitionResponse
import com.itecknologi.itecktestingcompose.apiFunctions.getEventLogID
import com.itecknologi.itecktestingcompose.functions.getLocation
import com.itecknologi.itecktestingcompose.appPrefs.PreferenceManager
import com.itecknologi.itecktestingcompose.functions.BottomLogo
import com.itecknologi.itecktestingcompose.functions.resetAllData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TestingPage(navController: NavHostController, context: Context, prefs: PreferenceManager) {

    var checkForEventLog by remember { mutableIntStateOf(0) }

    LaunchedEffect(checkForEventLog) {
        getEventLogID()
        Log.d("event log ", "event log id: ${Constants.eventLogID}")
        if (Constants.eventLogID == "") // calling function again if event log id is null or empty
        {
            checkForEventLog += 1
        }
    }


    getLocation()

    val name = prefs.getTechnicianName()
    var isLoggingOut by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isLoggingOut) 0f else 1f,
        animationSpec = tween(durationMillis = 500),
        label = "logoutAnimation"
    )

    var obdType by remember { mutableStateOf("Select Device Type") }

    var comp by remember { mutableStateOf(false) }

    var showDialogueReset by remember { mutableStateOf(false) }
    val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isLocationEnabled =
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    HandleDoubleBackToExit()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF122333)) // Dark blue background
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
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
                    Text(
                        name,
                        color = Color(0XFF39B54A),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
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

            }

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
                    popUpTo("initialPicturesScreen") { inclusive = true }
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
                        DropdownField_forDeviceTypeSelection(
                            selectedOption = obdType,
                            onOptionSelected = { obdType = it }
                        )

                    }

                }

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (obdType != "Select Device Type") {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0XFF182b3c), shape = RoundedCornerShape(24.dp))
                    .padding(18.dp)
            ) {
                ValidationStatusUI(obdType, onTestingCompleted = { result ->
                    comp = result
                }, prefs, isLocationEnabled)
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
        ) {
            Button(
                onClick = {
                    showDialogueReset = true
                },
                elevation = ButtonDefaults.buttonElevation(15.dp, 10.dp, 10.dp, 10.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF122333)),
                shape = RoundedCornerShape(50), border = BorderStroke(1.5.dp, Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = " Change Device ?",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 21.sp
                )
            }

            Button(
                enabled = comp,
                onClick = {
                    navController.navigate("finalPicturesScreen") {
                        popUpTo("testingPage") {
                            inclusive = true
                        }
                    }
                },
                elevation = ButtonDefaults.buttonElevation(15.dp, 10.dp, 10.dp, 10.dp),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                    contentColor = Color.White, containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(50),
                border = if (comp) {
                    BorderStroke(1.5.dp, Color(0XFF39B54A))
                } else null
            ) {
                Text(
                    text = "Proceed",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 21.sp
                )
            }
        }

        if (showDialogueReset) {
            AlertForDeviceChange(
                title = "Change Of Tracker",
                text = "Do you want to change the Tracker Device?",
                onDismiss = { showDialogueReset = false },
                navController = navController
            )

        }
        Spacer(modifier = Modifier.weight(1f))
        BottomLogo()
    }

}


@Composable
fun ValidationStatusUI(
    obdType: String,
    onTestingCompleted: (Boolean) -> Unit,
    prefs: PreferenceManager,
    isLocationEnabled: Boolean
) {
    var showBattery by remember { mutableStateOf(false) }
    var showIgnition by remember { mutableStateOf(false) }
    var showLocation by remember { mutableStateOf(false) }
    var showRelay by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    var deviceLocationResult by remember {
        mutableStateOf(
            ValidateLocationResponse()
        )
    }

    var batteryResult by remember {
        mutableStateOf(
            BatteryResponse()
        )
    }

    var ignitionResult by remember {
        mutableStateOf(
            IgnitionResponse()
        )
    }

    var relayResult by remember {
        mutableStateOf(
            RelayResponse()
        )
    }
    var startTimer by remember { mutableStateOf(false) }
    var cmdQueueResult by remember { mutableStateOf("") }
    val context = LocalContext.current

    var loc by remember { mutableStateOf(false) }

    var locResult by remember { mutableDoubleStateOf(0.1) }
    var moveToNextValidationStep by remember { mutableIntStateOf(0) } // 0 = loc, 1 = battery, 2 = ignition, 3 = relay
//    checkLocationWithinRange()
    var enableColorForBar by remember { mutableStateOf(false) }

    if (loc) {
        getLocation()
        locResult = checkLocationWithinRange()
        loc = false
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0XFF182b3c))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            val rowModifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)

            // LOCATION Wali Row

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = rowModifier
            ) {

                Text(
                    "Location",
                    fontSize = 12.sp,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(0.15f)
                        .wrapContentSize(Alignment.CenterStart)
                )
                LinearProgressIndicator(
                    progress = { 1f },
                    color = when {
                        // 1–100 and success (not loading)
                        locResult in 1.0..100000.0 &&
                                deviceLocationResult.Success &&
                                !deviceLocationResult.isLoading && enableColorForBar -> Color(
                            0xFF39B54A
                        )

                        // Not successful and finished loading
                        !deviceLocationResult.Success &&
                                !deviceLocationResult.isLoading && enableColorForBar -> Color.Red

                        // Distance more than 100 and finished loading
                        locResult > 100000.0 &&
                                !deviceLocationResult.isLoading && enableColorForBar -> Color.Red

                        // Default (loading or unknown)
                        else -> Color.LightGray
                    },
                    modifier = Modifier
                        .weight(0.45f)
                        .clip(RoundedCornerShape(50))
                        .wrapContentSize()
                        .height(10.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = if (moveToNextValidationStep == 0 && !deviceLocationResult.isLoading) Color.White else Color.Transparent,
                    modifier = Modifier
                        .size(22.dp)
                        .clickable(enabled = moveToNextValidationStep == 0 && !deviceLocationResult.isLoading) {
                            if (isLocationEnabled) {
                                enableColorForBar = true
                                if (prefs.getTechnicianID() == 0) {
                                    locResult = 5.00
                                } else {
                                    showLocation = true
                                    loc = true
                                    deviceLocationResult =
                                        ValidateLocationResponse(
                                            isLoading = true, //Just yeh loader dikhane k lie hy
                                            Lat = 0.0,
                                            Lng = 0.0,
                                            Message = "",
                                            Success = false
                                        )
                                    coroutineScope.launch {
                                        deviceLocationResult = validateLoc(Constants.deviceID)
                                        Constants.deviceLocationLat = deviceLocationResult.Lat
                                        Constants.deviceLocationLong = deviceLocationResult.Lng
                                        Constants.deviceLocation = deviceLocationResult.Message

                                    }
                                }
                            } else {
                                Toast.makeText(context, "Location is OFF", Toast.LENGTH_SHORT)
                                    .show()
                                enableColorForBar = false
                            }


                        }

                )
                if ((locResult in 1.00..100000.00 && deviceLocationResult.Success && isLocationEnabled) || locResult == 5.00) {
                    moveToNextValidationStep =
                        1
                }

            }

            // BATTERY Wali Row

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = rowModifier
            ) {
                //THREE STAGES FOR BATTERY VALIDATION
                var batteryProgress by remember { mutableFloatStateOf(0.0f) }

                batteryProgress = when {
                    batteryResult.battery == "Connected" && batteryProgress == 0.0f -> 0.30f
                    batteryResult.battery == "Disconnected" && batteryProgress == 0.30f -> 0.60f
                    batteryResult.battery == "Connected" && batteryProgress == 0.60f -> 1.0f
                    else -> batteryProgress
                }

                if (batteryProgress == 1f && obdType == "OBD") {
                    onTestingCompleted(true)
                } else if (batteryProgress == 1f && (obdType == "IMMOBILIZER" || obdType == "LOCATION"))
                    moveToNextValidationStep = 2

                Text(
                    "Battery",
                    fontSize = 12.sp,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(0.15f)
                        .wrapContentSize(Alignment.CenterStart)
                )
                LinearProgressIndicator(
                    progress = { batteryProgress },
                    color = if (batteryResult.battery == "Disconnected" || batteryResult.battery == "Connected" || prefs.getTechnicianID() == 0) Color(
                        0XFF39B54A
                    ) else if (batteryResult.battery == "Battery status is null") Color.Red else Color.LightGray,
                    modifier = Modifier
                        .weight(0.45f)
                        .clip(RoundedCornerShape(50))
                        .wrapContentSize()
                        .height(10.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = if (moveToNextValidationStep == 1 && !batteryResult.isLoading) Color.White else Color.Transparent,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(enabled = moveToNextValidationStep == 1 && !batteryResult.isLoading) {
                            if (prefs.getTechnicianID() == 0) {
                                batteryProgress = 1.0f
                                moveToNextValidationStep = 2
                            } else {
                                batteryResult = BatteryResponse(isLoading = true, battery = "")
                                showBattery = true
                                showLocation = false
                                coroutineScope.launch {
                                    batteryResult = if (Constants.eventLogID != "") {
                                        validateBattery(
                                            Constants.deviceID,
                                            if (batteryProgress == 0.0f) 1 else if (batteryProgress == 0.30f) 0 else 1,
                                            Constants.eventLogID
                                        )
                                    } else {
                                        BatteryResponse(false, "Event Log Not Found")
                                    }

                                }
                            }
                        }
                )

            }

            if (obdType != "OBD") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = rowModifier
                ) {
                    //THREE STAGES FOR IGNITION VALIDATION
                    var ignitionProgress by remember { mutableFloatStateOf(0.0f) }
                    ignitionProgress = when {
                        ignitionResult.ignition == "ON" && ignitionProgress == 0.0f -> 0.30f
                        ignitionResult.ignition == "OFF" && ignitionProgress == 0.30f -> 0.60f
                        ignitionResult.ignition == "ON" && ignitionProgress == 0.60f -> 1.0f
                        else -> ignitionProgress
                    }
                    if (ignitionProgress == 1f) {
                        if (obdType == "LOCATION") {
                            onTestingCompleted(true)
                        } else moveToNextValidationStep = 3
                    }
//                    else
//                        onTestingCompleted(false)
                    Text(
                        "Ignition",
                        fontSize = 12.sp,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(0.15f)
                            .wrapContentSize(Alignment.CenterStart)
                    )
                    LinearProgressIndicator(
                        progress = { ignitionProgress },
                        color = if (ignitionResult.ignition == "ON" || ignitionResult.ignition == "OFF" || prefs.getTechnicianID() == 0) Color(
                            0XFF39B54A
                        ) else Color.LightGray,
                        modifier = Modifier
                            .weight(0.45f)
                            .clip(RoundedCornerShape(50))
                            .wrapContentSize()
                            .height(10.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        tint = if (moveToNextValidationStep == 2 && !ignitionResult.isLoading) Color.White else Color.Transparent,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(enabled = moveToNextValidationStep == 2 && !ignitionResult.isLoading) {
                                if (prefs.getTechnicianID() == 0) {
                                    ignitionProgress = 1.0f
                                    moveToNextValidationStep = 3
                                } else {
                                    ignitionResult =
                                        IgnitionResponse(isLoading = true, ignition = "")
                                    showIgnition = true
                                    showBattery = false
                                    coroutineScope.launch {
                                        ignitionResult = validateIgnition(Constants.deviceID)
                                    }
                                }

                            }
                    )

                }
            }
            var showConfirmationForKill by remember { mutableStateOf(false) }
            var showConfirmationForRelease by remember { mutableStateOf(false) }

            // RELAY Wali Row
            if (obdType == "IMMOBILIZER") {

                var relayProgress by remember { mutableFloatStateOf(0.0f) }
                var timerStarted by remember { mutableStateOf(false) }

                LaunchedEffect(cmdQueueResult) {
                    when {
                        cmdQueueResult == "Command Not in queue" && relayProgress == 0.0f -> {
                            showConfirmationForKill = true
                            cmdQueueResult = ""
                        }

                        cmdQueueResult == "Command Not in queue" && relayProgress == 0.5f -> {
                            showConfirmationForRelease = true

                        }

                        else -> {
                            // Any other response
                            onTestingCompleted(false)
                        }

                    }
                }
                if (showConfirmationForKill) {
                    AlertDialog(
                        onDismissRequest = { },
                        title = { Text("Confirmation") },
                        text = { Text("Is the Vehicle physically Killed?") },
                        confirmButton = {
                            TextButton(onClick = {
                                showConfirmationForKill = false
                                relayProgress = 0.5f
                            }) {
                                Text("Yes")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showConfirmationForKill = false }) {
                                Text("No")
                            }
                        }
                    )
                }
                if (showConfirmationForRelease) {
                    AlertDialog(
                        onDismissRequest = { },
                        title = { Text("Confirmation") },
                        text = { Text("Is the Vehicle physically Released?") },
                        confirmButton = {
                            TextButton(onClick = {
                                showConfirmationForRelease = false
                                relayProgress = 1f
                                onTestingCompleted(true)
                                showRelay = false
                            }) {
                                Text("Yes")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showConfirmationForRelease = false }) {
                                Text("No")
                            }
                        }
                    )
                }

                // Timer coroutine
                LaunchedEffect(startTimer) {
                    if (startTimer && !timerStarted && prefs.getTechnicianID() != 0) {
                        timerStarted = true
                        while (true) {
                            cmdQueueResult = cmdQueueCheck(
                                Constants.deviceID,
                                if (relayProgress == 0.0f) "kill" else "release"
                            )
                            if (cmdQueueResult == "Command Not in queue") break
                            delay(1000)
                        }
                        timerStarted = false
                        startTimer = false
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = rowModifier
                ) {
                    Text(
                        "Relay",
                        fontSize = 12.sp,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(0.15f)
                            .wrapContentSize(Alignment.CenterStart)
                    )
                    LinearProgressIndicator(
                        progress = { relayProgress },
                        color = Color(
                            0XFF39B54A
                        ),
                        modifier = Modifier
                            .weight(0.45f)
                            .clip(RoundedCornerShape(50))
                            .wrapContentSize()
                            .height(10.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    if (relayProgress < 1f) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = if (moveToNextValidationStep == 3 && !relayResult.isLoading && !startTimer && !showConfirmationForKill && !showConfirmationForRelease) Color.White else Color.Transparent,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(enabled = moveToNextValidationStep == 3 && !relayResult.isLoading && !startTimer) {
                                    if (prefs.getTechnicianID() == 0) {
                                        relayProgress = 1.0f
                                        onTestingCompleted(true)
                                    } else {
                                        showRelay = true
                                        showIgnition = false
                                        relayResult =
                                            RelayResponse(
                                                success = false,
                                                isLoading = true,
                                                message = ""
                                            )
                                        coroutineScope.launch {
                                            if (relayProgress == 0.0f) {
                                                relayResult =
                                                    setRelayStatus(Constants.deviceID, "kill")
                                                if (relayResult.success) {
                                                    startTimer = true
                                                }


                                            } else if (relayProgress == 0.5f) {
                                                relayResult =
                                                    setRelayStatus(
                                                        Constants.deviceID,
                                                        cmd = "release"
                                                    )
                                                if (relayResult.success) {
                                                    startTimer = true
                                                }
                                            }

                                        }
                                    }

                                }
                        )
                    } else
                        Spacer(modifier = Modifier.width(24.dp))
                }
            }

        }
        Spacer(modifier = Modifier.height(5.dp))
        if (deviceLocationResult.isLoading || batteryResult.isLoading || ignitionResult.isLoading || relayResult.isLoading || startTimer) {
            Text(
                "Please Wait..",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold, lineHeight = 26.sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val text = when {
                    showLocation -> when {
                        !isLocationEnabled -> "Please Turn ON Location Services"
                        Constants.deviceLocationLat != 0.0 && Constants.deviceLocationLong != 0.0 -> "Location: ${deviceLocationResult.Message}"
                        else -> deviceLocationResult.Message
                    }

                    showBattery -> "Battery: ${batteryResult.battery}"
                    showIgnition -> "Ignition: ${ignitionResult.ignition}"
                    showRelay -> relayResult.message
                    else -> ""
                }

                if (!deviceLocationResult.isLoading && !batteryResult.isLoading && !ignitionResult.isLoading && !relayResult.isLoading) {
                    Text(
                        text = text,
                        color = if (text == "Battery: Disconnected" || text.contains("Data not Updating Last Gps time:") || text == "Ignition: OFF" || text == "Location: Failed" || text == "Command already in queue" || text == "Error Sending Command!" || text == "Location: GPS Invalid") Color.Red else Color(
                            0XFF39B54A
                        ),
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 5,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center, lineHeight = 26.sp
                    )
                }

            }

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField_forDeviceTypeSelection(
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val options = listOf("IMMOBILIZER", "LOCATION", "OBD")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            enabled = true, // allow selection
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .height(65.dp),
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color(0xFF000000),
                unfocusedTextColor = Color(0xFF000000),
                disabledTextColor = Color(0xFF000000),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onOptionSelected(selectionOption)
                        Constants.immobilizer = when (selectionOption) {
                            "IMMOBILIZER" -> 1
                            else -> 0
                        }
                        Constants.installedDeviceType = when (selectionOption) {
                            "OBD" -> 2
                            else -> 0
                        }
                        expanded = false
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TestingPagePreview() {
    TestingPage(
        rememberNavController(),
        LocalContext.current,
        PreferenceManager(LocalContext.current)
    )
}

@Composable
fun AlertForDeviceChange(
    title: String,
    text: String,
    onDismiss: () -> Unit,
    navController: NavHostController
) {

    AlertDialog(
        title = {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                textAlign = TextAlign.Start,
                fontSize = 26.sp
            )
        },
        text = {
            Text(
                text = text,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                textAlign = TextAlign.Start,
                fontSize = 20.sp
            )
        },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    when (Constants.navigateToScreen) {
                        "Installation" -> {
                            navController.navigate("Device Entry For New Installation") {
                                popUpTo("testingPage") {
                                    inclusive = true
                                }
                            }
                        }

                        "Redo" -> {
                            navController.navigate("Device Entry For New Installation") {
                                popUpTo("testingPage") {
                                    inclusive = true
                                }
                            }
                        }

                        "Removal" -> {
                            navController.navigate("Device Entry For New Installation") {
                                popUpTo("testingPage") {
                                    inclusive = true
                                }
                            }
                        }

                        else -> {}
                    }

                    Constants.deviceID = ""
                    Constants.initialPictures = mutableStateListOf(null, null)
                    Constants.deviceLocationLat = 0.0
                    Constants.deviceLocationLong = 0.0
                    Constants.deviceLocation = ""
                    Constants.eventLogID = ""
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(25.dp, 10.dp)
            ) {
                Text(
                    "Yes",
                    color = Color(0xFF122333),
                    fontSize = 20.sp
                )
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(25.dp, 10.dp)
            ) {
                Text(
                    "Cancel",
                    color = Color(0xFF122333),
                    fontSize = 20.sp
                )
            }
        },
        containerColor = Color(0xFF122333),
        titleContentColor = Color.White
    )


}
