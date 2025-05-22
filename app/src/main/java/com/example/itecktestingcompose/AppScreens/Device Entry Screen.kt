package com.example.itecktestingcompose.AppScreens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import kotlinx.coroutines.launch
import com.example.itecktestingcompose.APIFunctions.VehicleValidationResult
import com.example.itecktestingcompose.APIFunctions.getVehicleDetails
import com.example.itecktestingcompose.ModelClasses.VehData
import com.example.itecktestingcompose.appPrefs.PreferenceManager
import com.example.itecktestingcompose.functions.resetAllData
import kotlinx.coroutines.delay


@SuppressLint("UseKtx")
@Composable
fun DeviceEntryScreen(
    context: Context,
    navController: NavHostController,
    prefs: PreferenceManager
) {

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

    var VehicleDetailsResult by remember {
        mutableStateOf(
            VehicleValidationResult(
                false,
                "",
                data = emptyList(),
                false
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
                        name,
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
                            .background(Color(0XFF39B54A)) // Green search
                            .clickable(enabled = vehicleEngineChassis != "") {
                                keyboard?.hide()
                                VehicleDetailsResult = VehicleValidationResult(
                                    false,
                                    "",
                                    data = emptyList(),
                                    true
                                )

                                couroutineScope.launch {
                                    VehicleDetailsResult =
                                        getVehicleDetails(vehicleEngineChassis)

                                    if (VehicleDetailsResult.ifDetailsExist) {

                                        showVehicleCards = true

                                        Toast.makeText(
                                            context,
                                            "Vehicle Details Found Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        showVehicleCards = false
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
                                if (enableDeviceNumberEntry) Color(
                                    0XFF39B54A
                                ) else Color.Gray
                            ) // Green search
                            .clickable(enabled = (devID != "" && showVehicleCards)) {
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
        Spacer(modifier = Modifier.height(12.dp))
        if (showVehicleCards) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(370.dp)
                    .background(Color(0xFF122333), shape = RoundedCornerShape(24.dp))
                    .padding(10.dp)
            ) {
                VehicleListScreen(
                    vehicleList = VehicleDetailsResult.data,
                    onSelectionChanged = { isSelected, vehicleID ->
                        enableDeviceNumberEntry = isSelected
                        Constants.vehicleID = vehicleID ?: ""
                    })
            }

        }
        if (VehicleDetailsResult.isLoading) {
            CircularProgressIndicator(
                color = Color.Green,
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp
            )
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
    var selectedVehicle by rememberSaveable { mutableStateOf<VehData?>(null) }

    LazyColumn {
        items(vehicleList) { vehicle ->
            VehicleCard(
                vehicle = vehicle,
                isSelected = vehicle == selectedVehicle,
                onClick = {
                    val isSame = vehicle == selectedVehicle
                    selectedVehicle = if (isSame) null else vehicle
                    onSelectionChanged(!isSame, if (!isSame) vehicle.V_ID else null)
                }
            )
        }
    }
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

