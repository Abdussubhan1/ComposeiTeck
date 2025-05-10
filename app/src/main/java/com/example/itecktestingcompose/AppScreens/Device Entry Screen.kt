package com.example.itecktestingcompose.AppScreens

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.itecktestingcompose.APIFunctions.DevValidationResult
import com.example.itecktestingcompose.APIFunctions.validateDev
import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.functions.HandleDoubleBackToExit
import com.example.itecktestingcompose.R
import com.example.itecktestingcompose.Mainactivity.jameelNooriFont
import kotlinx.coroutines.launch
import androidx.core.content.edit
import com.example.itecktestingcompose.APIFunctions.VehicleValidationResult
import com.example.itecktestingcompose.APIFunctions.getVehicleDetails
import com.example.itecktestingcompose.functions.resetAllData
import kotlinx.coroutines.delay


@SuppressLint("UseKtx")
@Composable
fun DeviceEntryScreen(context: Context, navController: NavHostController) {

    val sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val sharePref =
        context.getSharedPreferences(
            "TechnicianName",
            Context.MODE_PRIVATE
        )
    val name = sharePref.getString("Name", "")

    val sharePref1 =
        context.getSharedPreferences("UserCNIC", Context.MODE_PRIVATE)



    val hasNewNotification =
        remember { mutableStateOf(sharedPref.getBoolean("hasNewNotification", false)) }

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
    var isEngineEnabled by remember { mutableStateOf(true) }
    var testingStart by remember { mutableStateOf(false) }
    var showTicket by rememberSaveable { mutableStateOf(false) }

    var VehicleDetailsResult by remember {
        mutableStateOf(
            VehicleValidationResult(
                false,
                "",
                emptyList()
            )
        )
    }


    HandleDoubleBackToExit() //this is used to ensure secure exit from app

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
                        name.toString(),
                        color = Color(0XFF39B54A),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                Icon(
                    imageVector = Icons.Default.NotificationImportant,
                    contentDescription = "Tips",
                    tint = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            navController.navigate("NotificationScreen")
                            sharedPref.edit { putBoolean("hasNewNotification", false) }
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
                sharePref1.edit { putString("CNIC", "") }
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
                            if (Constants.EngineChassis == "") "Engine/Chassis" else Constants.EngineChassis,
                            onValueChange = { vehicleEngineChassis = it },
                            isEngineEnabled,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )

                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0XFF39B54A)) // Green search
                            .clickable(enabled = vehicleEngineChassis != "") {
                                keyboard?.hide()
                                couroutineScope.launch {
                                    VehicleDetailsResult = getVehicleDetails(vehicleEngineChassis)

                                    if (VehicleDetailsResult.ifDetailsExist) {

                                        showTicket = true

                                        Constants.EngineChassis = vehicleEngineChassis
                                        Constants.Vehmake = VehicleDetailsResult.data[0].MAKE
                                        Constants.Vehmodel = VehicleDetailsResult.data[0].MODEL
                                        Constants.VehColor = VehicleDetailsResult.data[0].COLOR
                                        Constants.Vehyear = VehicleDetailsResult.data[0].YEAR


                                        Toast.makeText(
                                            context,
                                            "Vehicle Details Found Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        showTicket = false
                                        testingStart = false
                                        Toast.makeText(
                                            context,
                                            VehicleDetailsResult.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
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
                            showTicket,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                if (showTicket) Color(
                                    0XFF39B54A
                                ) else Color.Gray
                            ) // Green search
                            .clickable(enabled = (devID != "" && showTicket)) {
                                keyboard?.hide()
                                validationResult = DevValidationResult(
                                    ifDeviceExist = false,
                                    isLoading = true
                                )
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

        if (showTicket) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFF90A4AE)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF102027)) // darker background for contrast
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Vehicle Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFBBDEFB),
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    val textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFFAFAFA),
                        textAlign = TextAlign.Start
                    )

                    val values = listOf(
                        "Make" to Constants.Vehmake,
                        "Model" to Constants.Vehmodel,
                        "Color" to Constants.VehColor,
                        "Year" to Constants.Vehyear
                    )

                    values.forEach { (label, value) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .background(Color(0xFF263238), RoundedCornerShape(10.dp))
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$label:",
                                style = textStyle,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = value,
                                style = textStyle.copy(
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF80D8FF)
                                ),
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }

        }



        Spacer(modifier = Modifier.height(18.dp))

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
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = keyboardOptions,
        singleLine = true
    )
}


@Preview
@Composable
fun MainScreenPreview() {
    DeviceEntryScreen(LocalContext.current, rememberNavController())
}

