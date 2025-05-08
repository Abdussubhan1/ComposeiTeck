package com.example.itecktestingcompose.AppScreens


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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.itecktestingcompose.APIFunctions.ValidateLocationResponse
import com.example.itecktestingcompose.APIFunctions.batteryResponse
import com.example.itecktestingcompose.APIFunctions.ignitionResponse
import com.example.itecktestingcompose.APIFunctions.validateBattery
import com.example.itecktestingcompose.APIFunctions.validateIgnition
import com.example.itecktestingcompose.APIFunctions.validateLoc
import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.functions.HandleDoubleBackToExit
import com.example.itecktestingcompose.functions.checkLocationWithinRange
import com.example.itecktestingcompose.R
import com.example.itecktestingcompose.functions.getLocation
import com.example.itecktestingcompose.Mainactivity.jameelNooriFont
import kotlinx.coroutines.launch

@Composable
fun TestingPage(navController: NavHostController) {
    getLocation()

    var obdType by remember { mutableStateOf("Select OBD Type") }

    var comp by remember { mutableStateOf(false) }

    var showDialogueReset by remember { mutableStateOf(false) }
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
                        Constants.name,
                        color = Color(0XFF39B54A),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
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
                        DropdownField(
                            selectedOption = obdType,
                            onOptionSelected = { obdType = it }
                        )

                    }

                }

            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        if (obdType != "Select OBD Type") {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0XFF182b3c), shape = RoundedCornerShape(24.dp))
                    .padding(18.dp)
            ) {
                ValidationStatusUI(obdType, onTestingCompleted = { result ->
                    comp = result
                })
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
                    text = " ڈیوائس تبدیل کریں",
                    fontFamily = jameelNooriFont,
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
                    text = " آگے بڑھیں۔",
                    fontFamily = jameelNooriFont,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 21.sp
                )
            }
        }

        if (showDialogueReset) {
            Alert(
                title = "ٹریکر کی تبدیلی",
                text = "کیا آپ واقعی ٹریکر تبدیل کرنا چاہتے ہیں؟",
                onDismiss = { showDialogueReset = false },
                navController = navController
            )

        }
        Spacer(modifier = Modifier.weight(1f))
        BottomLogo()
    }

}


@Composable
fun ValidationStatusUI(obdType: String, onTestingCompleted: (Boolean) -> Unit) {
    var showBattery by remember { mutableStateOf(false) }
    var showIgnition by remember { mutableStateOf(false) }
    var showLocation by remember { mutableStateOf(false) }
    var showRelay by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    var deviceLocationResult by remember {
        mutableStateOf(
            ValidateLocationResponse(
                isLoading = false,
                Lat = 0.0,
                Lng = 0.0,
                Message = "",
                Success = false
            )
        )
    }

    var batteryResult by remember {
        mutableStateOf(
            batteryResponse(
                isLoading = false,
                battery = ""
            )
        )
    }

    var ignitionResult by remember {
        mutableStateOf(
            ignitionResponse(
                isLoading = false,
                ignition = ""
            )
        )
    }

//Relay result yaha banana


    var loc by remember { mutableStateOf(false) }
    var locResult by remember { mutableDoubleStateOf(0.1) }
    var moveToNextValidationStep by remember { mutableIntStateOf(0) } // 0 = loc, 1 = battery, 2 = ignition, 3 = relay
    checkLocationWithinRange()

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
                    color = if (locResult in 1.00..100.00) Color(0XFF39B54A)
                    else if ((locResult > 100.00) && !deviceLocationResult.isLoading) Color.Red
                    else Color.LightGray,
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

                )
                if (locResult in 1.00..100.00 && deviceLocationResult.Success) {
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
                if (batteryProgress == 1f) moveToNextValidationStep = 2

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
                    color = if (batteryResult.battery == "Disconnected" || batteryResult.battery == "Connected") Color(
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
                    tint = if (moveToNextValidationStep == 1 && !batteryResult.isLoading) Color.White else Color.Transparent,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(enabled = moveToNextValidationStep == 1 && !batteryResult.isLoading) {
                            batteryResult = batteryResponse(isLoading = true, battery = "")
                            showBattery = true
                            showLocation = false
                            coroutineScope.launch {
                                batteryResult = validateBattery(Constants.deviceID)
                            }
                        }
                )

            }

            // IGNITION Wali Row
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
                    if (obdType == "OBD" || obdType == "OBD With Wires") {
                        onTestingCompleted(true)
                    } else moveToNextValidationStep = 3
                }
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
                    color = if (ignitionResult.ignition == "ON" || ignitionResult.ignition == "OFF") Color(
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
                            ignitionResult = ignitionResponse(isLoading = true, ignition = "")
                            showIgnition = true
                            showBattery = false
                            coroutineScope.launch {
                                ignitionResult = validateIgnition(Constants.deviceID)
                            }

                        }
                )

            }

            // RELAY Wali Row
            if (obdType == "NA") {
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
                        progress = { 1f },
                        color = Color.LightGray,
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
                        tint = if (moveToNextValidationStep == 3) Color.White else Color.Transparent,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(enabled = moveToNextValidationStep == 3) {
//                                onTestingCompleted(true)
                                showRelay = true
                                showIgnition = false
                                coroutineScope.launch {
//                                    relay = validateRelay(Constants.deviceID)
                                }
                            }
                    )

                }
            }


        }
        Spacer(modifier = Modifier.height(5.dp))
        if (deviceLocationResult.isLoading || batteryResult.isLoading || ignitionResult.isLoading) {
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
                    showLocation -> "Location: ${deviceLocationResult.Message}"
                    showBattery -> "Battery: ${batteryResult.battery}"
                    showIgnition -> "Ignition: ${ignitionResult.ignition}"
                    showRelay -> "Relay: ${"Connected"}"
                    else -> ""
                }

                if (!deviceLocationResult.isLoading && !batteryResult.isLoading && !ignitionResult.isLoading) {
                    Text(
                        text = text,
                        color = if (text == "Battery: Disconnected" || text == "Ignition: OFF" || text == "Location: Failed") Color.Red else Color(
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
fun DropdownField(
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val options = listOf("NA", "OBD", "OBD With Wires")
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
    TestingPage(rememberNavController())
}

@Composable
fun Alert(
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
                fontFamily = jameelNooriFont,
                textAlign = TextAlign.End,
                fontSize = 26.sp
            )
        },
        text = {
            Text(
                text = text,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                fontFamily = jameelNooriFont,
                textAlign = TextAlign.End,
                fontSize = 20.sp
            )
        },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    navController.navigate("mainscreen")
                    Constants.deviceID = ""
                    Constants.initialPictures = mutableStateListOf(null, null)
                    Constants.deviceLocationLat = 0.0
                    Constants.deviceLocationLong = 0.0
                    Constants.deviceLocation = ""
                    Constants.EngineChassis = ""
                    Constants.Vehmake = ""
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(25.dp, 10.dp)
            ) {
                Text(
                    "جی ہاں",
                    color = Color(0xFF122333), // Text color contrasting with white background
                    fontFamily = jameelNooriFont,
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
                    "نہیں",
                    color = Color(0xFF122333),
                    fontFamily = jameelNooriFont,
                    fontSize = 20.sp
                )
            }
        },
        containerColor = Color(0xFF122333),
        titleContentColor = Color.White
    )



}

@Preview
@Composable
fun AlertPreview() {
    Alert("Title", "Text", {}, rememberNavController())
}
