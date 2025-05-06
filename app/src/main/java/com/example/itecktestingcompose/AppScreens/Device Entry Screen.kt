package com.example.itecktestingcompose.AppScreens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.NotificationImportant
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


@SuppressLint("UseKtx")
@Composable
fun DeviceEntryScreen(context: Context, navController: NavHostController) {

    val sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val hasNewNotification =
        remember { mutableStateOf(sharedPref.getBoolean("hasNewNotification", false)) }

    var devID by remember { mutableStateOf("") }
    var vehicleEngineChassis by remember { mutableStateOf("") }

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
    var showTicket by remember { mutableStateOf(false) }
    var pictureTaking by remember { mutableStateOf(false) }
    var initiallistCompleted by remember { mutableStateOf(false) }
    var initiallistOfImages = remember { mutableStateListOf<Bitmap?>(null, null) }
    var moveToTesting by remember { mutableStateOf(false) }

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
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
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
                        Constants.name,
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


        }

        Spacer(modifier = Modifier.height(40.dp))

        // Rounded card background
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
                            "Device Number",
                            onValueChange = { devID = it },
                            isEnabled,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0XFF39B54A)) // Green search
                            .clickable(enabled = devID != "") {
                                keyboard?.hide()
                                validationResult = DevValidationResult(
                                    ifDeviceExist = false,
                                    isLoading = true
                                )
                                couroutineScope.launch {

                                    validationResult = validateDev(devID)

                                    if (validationResult.ifDeviceExist) {
                                        isEnabled = false
                                        Constants.deviceID = devID

                                        Toast.makeText(
                                            context,
                                            "Device ID is valid",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
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
//        Spacer(modifier = Modifier.height(32.dp))
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
                                        testingStart = true
                                        showTicket = true


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
            }
        }
        if (showTicket) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .height(500.dp),
                border = BorderStroke(2.dp, Color(0xFFB0BEC5)),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Vehicle Details",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF37474F),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    val textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF263238),
                        textAlign = TextAlign.Center
                    )

                    listOf(
                        "Make:\n${Constants.Vehmake}",
                        "Model:\n${Constants.Vehmodel}",
                        "Color:\n${Constants.VehColor}",
                        "Year:\n${Constants.Vehyear}",
                        "OBD status: INPROGRESS"
                    ).forEach { label ->
                        Text(
                            text = label,
                            style = textStyle,
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 12.dp)
                                .fillMaxWidth()
                                .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        )
                    }
                }
            }
        }



        Spacer(modifier = Modifier.height(18.dp))

        // Button
        Button(
            onClick = { pictureTaking = true },
            enabled = testingStart && !isEnabled,
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
        if (pictureTaking) {
            val cameraLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.TakePicturePreview()
            ) { result ->
                if (result != null) {
                    val firstNullIndex = initiallistOfImages.indexOfFirst { it == null }

                    if (firstNullIndex != -1) {
                        initiallistOfImages[firstNullIndex] = result
                        Constants.initialPictures = initiallistOfImages
                    }
                } else {
                    Toast.makeText(context, "Capture Failed!", Toast.LENGTH_SHORT).show()
                }
            }
            val cameraPermissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    cameraLauncher.launch(null) // Launch camera
                } else {
                    Toast.makeText(context, "Camera Permission Denied!", Toast.LENGTH_SHORT).show()
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (initiallistOfImages[0] == null) {
                    Icon(
                        imageVector = Icons.Default.CameraEnhance,
                        contentDescription = "Camera Icon 1",
                        tint = Color(0XFF39B54A),
                        modifier = Modifier
                            .size(60.dp)
                            .padding(start = 14.dp)
                            .clickable {
                                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "ڈیوائس کی پہلی تصویر لینے کے لیے کلک کریں",
                        modifier = Modifier.padding(end = 14.dp),
                        fontFamily = jameelNooriFont,
                        maxLines = 2,
                        fontSize = 18.sp, textAlign = TextAlign.End, color = Color.White
                    )
                } else if (initiallistOfImages[1] == null) {
                    Icon(
                        imageVector = Icons.Default.CameraEnhance,
                        contentDescription = "Camera Icon 1",
                        tint = Color(0XFF39B54A),
                        modifier = Modifier
                            .size(60.dp)
                            .padding(start = 14.dp)
                            .clickable {
                                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = "ڈیوائس کی دوسری تصویر لینے کے لیے کلک کریں۔",
                        modifier = Modifier.padding(end = 14.dp),
                        fontFamily = jameelNooriFont,
                        fontSize = 18.sp, textAlign = TextAlign.End, color = Color.White
                    )
                } else
                    initiallistCompleted = true

            }

            Spacer(modifier = Modifier.height(28.dp))

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalArrangement = Arrangement.Center, // Center the content
                contentPadding = PaddingValues(horizontal = 0.dp) // No extra side padding
            ) {
                items(initiallistOfImages.size) { index ->
                    initiallistOfImages[index]?.let { bitmap ->
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Captured Images $index",
                            modifier = Modifier
                                .width(200.dp)
                                .height(200.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                                .padding(horizontal = 4.dp) // spacing between items
                        )
                    }
                }
            }

            if (initiallistCompleted && !moveToTesting) {
                PicConfirm(
                    initiallistOfImages,
                    onRetakeConfirmed = { initiallistCompleted = false },
                    navController
                )

            }
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
        keyboardOptions = keyboardOptions
    )
}


@Preview
@Composable
fun MainScreenPreview() {
    DeviceEntryScreen(LocalContext.current, rememberNavController())
}


@Composable
fun PicConfirm(
    initiallistOfImages: SnapshotStateList<Bitmap?>,
    onRetakeConfirmed: () -> Unit,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // optional for padding around content
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(28.dp)) // space between text and buttons

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
        ) {
            Button(
                onClick = {
                    initiallistOfImages.clear()
                    initiallistOfImages.addAll(listOf(null, null))
                    onRetakeConfirmed()
                },
                colors = ButtonDefaults.buttonColors(Color(0xFF122333)),
                shape = RoundedCornerShape(50), border = BorderStroke(1.dp, Color.Red),
                elevation = ButtonDefaults.buttonElevation(15.dp, 10.dp, 10.dp, 10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "دونوں تصویریں دوبارہ لیں۔",
                    fontFamily = jameelNooriFont,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 21.sp
                )
            }
            Button(
                onClick = {
                    navController.navigate("testingPage") {
                        popUpTo("mainscreen") {
                            inclusive = true
                        }
                    }
                },
                elevation = ButtonDefaults.buttonElevation(25.dp, 10.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF122333)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, Color(0XFF39B54A))
            ) {
                Text(
                    text = " آگے بڑھیں۔",
                    fontFamily = jameelNooriFont,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 21.sp
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PicConfirmPreview() {
    PicConfirm(
        remember { mutableStateListOf(null, null) },
        onRetakeConfirmed = {},
        rememberNavController()
    )
}

