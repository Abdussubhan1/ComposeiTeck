package com.itecknologi.itecktestingcompose.appScreens

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.itecknologi.itecktestingcompose.constants.Constants
import com.itecknologi.itecktestingcompose.functions.HandleDoubleBackToExit
import com.itecknologi.itecktestingcompose.R
import com.itecknologi.itecktestingcompose.mainActivity.jameelNooriFont
import com.itecknologi.itecktestingcompose.appPrefs.PreferenceManager
import com.itecknologi.itecktestingcompose.functions.resetAllData
import com.itecknologi.itecktestingcompose.interfaces.RetrofitInterface
import com.itecknologi.itecktestingcompose.modelClasses.TrackerLocationItem
import com.itecknologi.itecktestingcompose.objects.ServiceBuilder
import kotlinx.coroutines.delay


@Composable
fun FinalPicturesScreen(navController: NavController, prefs: PreferenceManager, context: Context) {

    HandleDoubleBackToExit()
    val name = prefs.getTechnicianName()
    var isLoggingOut by remember { mutableStateOf(false) }
    var custContact by remember { mutableStateOf("") }
    val current = LocalContext.current
    var FinallistCompleted by remember { mutableStateOf(false) }
    val FinallistOfImages = remember { mutableStateListOf<Bitmap?>(null, null) }
    var showFinalTicket by remember { mutableStateOf(value = false) }

    val alpha by animateFloatAsState(
        targetValue = if (isLoggingOut) 0f else 1f,
        animationSpec = tween(durationMillis = 500),
        label = "logoutAnimation"
    )
    var isValidLocation by remember { mutableStateOf(false) }
    var trackerInstallationLocation by remember { mutableStateOf("Select Tracker Location") }
    val trackerPlaces = remember { mutableStateListOf<TrackerLocationItem>() }

    LaunchedEffect(Unit) {
        try {
            val response = ServiceBuilder.buildService(RetrofitInterface::class.java)
                .getTrackerInstallLocation()

            if (response.isSuccessful && response.body() != null) {
                trackerPlaces.clear()
                trackerPlaces.addAll(response.body()!!)
            }
        } catch (e: Exception) {
            Log.e("Tracker Places", "Error: ${e.message}", e)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .verticalScroll(scroll)
            .background(Color(0xFF122333)) // Dark blue background
            .padding(horizontal = 16.dp),
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

        if (!showFinalTicket) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0XFF182b3c), shape = RoundedCornerShape(24.dp))
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        CustomTextField_screen2(
                            value = Constants.deviceID, onValueChange = {}
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF00C853)) // Green search
                            .clickable(enabled = false) {

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


            val cameraLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.TakePicturePreview()
            ) { result ->
                if (result != null) {
                    val firstNullIndex = FinallistOfImages.indexOfFirst { it == null }

                    if (firstNullIndex != -1) {
                        FinallistOfImages[firstNullIndex] = result
                        Constants.finalPictures = FinallistOfImages
                    }
                } else {
                    Toast.makeText(current, "Capture Failed!", Toast.LENGTH_SHORT).show()
                }
            }
            val cameraPermissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    cameraLauncher.launch(null) // Launch camera
                } else {
                    Toast.makeText(current, "Camera Permission Denied!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (FinallistOfImages[0] == null) {
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
                        text = "ڈیوائس کی تیسری تصویر لینے کے لیے کلک کریں",
                        modifier = Modifier.padding(end = 14.dp),
                        fontFamily = jameelNooriFont,
                        maxLines = 2,
                        fontSize = 18.sp, textAlign = TextAlign.End, color = Color.White
                    )
                } else if (FinallistOfImages[1] == null) {
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
                        text = "ڈیوائس کی چوتھی تصویر لینے کے لیے کلک کریں۔",
                        modifier = Modifier.padding(end = 14.dp),
                        fontFamily = jameelNooriFont,
                        fontSize = 18.sp, textAlign = TextAlign.End, color = Color.White
                    )
                } else
                    FinallistCompleted = true

            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalArrangement = Arrangement.Center,
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(FinallistOfImages.size) { index ->
                    FinallistOfImages[index]?.let { bitmap ->
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = "Captured Images $index",
                            modifier = Modifier
                                .width(200.dp) // Use fixed width instead of fillMaxWidth
                                .height(200.dp)
                                .clip(RoundedCornerShape(12.dp)) // Optional: make it prettier
                                .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                                .padding(horizontal = 4.dp)
                        )
                    }
                }
            }


            if (FinallistCompleted) {
                Spacer(modifier = Modifier.height(15.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterVertically
                    )
                ) {
                    Button(
                        onClick = {
                            FinallistOfImages.clear()
                            FinallistOfImages.addAll(listOf(null, null))
                            FinallistCompleted = false
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF122333)),
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.5.dp, Color.Red),
                        elevation = ButtonDefaults.buttonElevation(
                            15.dp,
                            10.dp,
                            10.dp,
                            10.dp
                        ),
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
                            showFinalTicket = true
                        },
                        elevation = ButtonDefaults.buttonElevation(25.dp, 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            Color(0xFF122333),
                            disabledContentColor = Color(0XFF122333)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.5.dp, Color(0XFF39B54A))
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
        if (showFinalTicket) {

            //Now all 4 pictures are contained in a Array
            val isValidContact =
                Regex("^(03[0-9]{9}|0[1-9]{2}[0-9]{7})$").matches(custContact)

            if (isValidContact) {
                Constants.cust_Contact = custContact
            } else
                Constants.cust_Contact = ""

            //For customer Contact Number

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0XFF182b3c), shape = RoundedCornerShape(24.dp))
                    .padding(16.dp)
            ) {
                TextField(
                    trailingIcon = {
                        if (!isValidContact) Icon(
                            Icons.Default.PhoneAndroid,
                            contentDescription = null,
                            tint = Color.Red
                        ) else Icon(
                            Icons.Filled.DoneAll,
                            contentDescription = null,
                            tint = Color.Green
                        )
                    },
                    value = custContact, onValueChange = { eachDigit ->
                        custContact = eachDigit
                    },
                    placeholder = {
                        Text(
                            "Enter Customer Mobile Number",
                            color = Color.DarkGray,
                            fontSize = 16.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(50),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color(0XFF000000),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color(0XFF182b3c), shape = RoundedCornerShape(24.dp))
                    .padding(16.dp)
            ) {

                DropdownField_forDeviceInstallationLocation(
                    options = trackerPlaces,
                    selectedOption = trackerInstallationLocation,
                    onOptionSelected = { selected ->
                        trackerInstallationLocation = selected
                    }
                )
                if (Constants.TLocID != 0)
                    isValidLocation = true

            }

            Spacer(modifier = Modifier.height(20.dp))

            if (isValidContact && isValidLocation) {
                Button(
                    onClick = {
                        navController.navigate("finalTicketScreen") {
                            popUpTo("finalPicturesScreen") {
                                inclusive = true
                            }
                        }
                    },
                    elevation = ButtonDefaults.buttonElevation(25.dp, 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFF122333),
                        disabledContentColor = Color(0XFF122333)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.5.dp, Color(0XFF39B54A))
                ) {
                    Text(
                        text = "Generate Ticket",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 21.sp
                    )
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField_forDeviceInstallationLocation(
    options: List<TrackerLocationItem>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .height(60.dp),
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
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.TLocName) },
                    onClick = {
                        onOptionSelected(item.TLocName)
                        Constants.TLocID = item.TLocId
                        expanded = false
                    }
                )
            }
        }
    }
}


@Composable
fun CustomTextField_screen2(
    value: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0XFF000000),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}


@Preview
@Composable
fun FinalPicturesScreenPreview() {
    FinalPicturesScreen(
        rememberNavController(),
        PreferenceManager(LocalContext.current),
        LocalContext.current
    )
}
