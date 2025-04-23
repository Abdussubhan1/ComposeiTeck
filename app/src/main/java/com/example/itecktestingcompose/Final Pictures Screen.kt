package com.example.itecktestingcompose

import android.Manifest
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.itecktestingcompose.Constants.Constants
import kotlinx.coroutines.launch

@Composable
fun FinalPicturesScreen() {
    var current = LocalContext.current
    var FinallistCompleted by remember { mutableStateOf(false) }
    var FinallistOfImages = remember { mutableStateListOf<Bitmap?>(null, null) }
    var showFinalTicket by remember { mutableStateOf(false) }
    var couroutineScope = rememberCoroutineScope()
    var statusResult by remember {
        mutableStateOf(
            StatusResult(
                GPSTime = "",
                GSMSignal = 0,
                Ignition = "",
                Location = "",
                PowerVoltages = ""
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                        Constants.name,
                        color = Color(0XFF39B54A),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
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

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            CustomTextField_screen2(
                                Constants.deviceID
                            ) {}

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

                    if (FinallistCompleted) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                                    )
                                }
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
                                    couroutineScope.launch {
                                        statusResult = getStatus(Constants.deviceID)
                                    }

                                },
                                elevation = ButtonDefaults.buttonElevation(25.dp, 10.dp),
                                colors = ButtonDefaults.buttonColors(Color(0xFF122333)),
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
            }

        }

        if (showFinalTicket) {
            Spacer(modifier = Modifier.height(5.dp))
            Card(
                modifier = Modifier
                    .width(350.dp)
                    .height(600.dp),
                border = BorderStroke(2.dp, Color(0xFFB0BEC5)),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                val scroll = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scroll)
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ticket",
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
                        "Device Number:\n\n${Constants.deviceID}",
                        "Location:\n\n${statusResult.Location}",
                        "GPS Time:\n\n${statusResult.GPSTime}",
                        "Main Voltages:\n\n${statusResult.PowerVoltages}",
                        "GSM Signals:\n\n${statusResult.GSMSignal} Volts",
                        "Ignition:\n\n${statusResult.Ignition}"
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
                    Text(
                        text = "Captured Images",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF546E7A),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Text(
                        text = "Initial Pictures",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF546E7A),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        items(Constants.initialPictures.size) { index ->
                            Constants.initialPictures[index]?.let { bitmap ->

                                    Image(
                                        bitmap = bitmap.asImageBitmap(),
                                        contentDescription = "Initial Picture $index",
                                        modifier = Modifier
                                            .width(180.dp)
                                            .height(180.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                                            .shadow(4.dp, RoundedCornerShape(16.dp))
                                    )


                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Final Pictures",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF546E7A),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        items(FinallistOfImages.size) { index ->
                            FinallistOfImages[index]?.let { bitmap ->

                                    Image(
                                        bitmap = bitmap.asImageBitmap(),
                                        contentDescription = "Captured Image $index",
                                        modifier = Modifier
                                            .width(180.dp)
                                            .height(180.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                                            .shadow(4.dp, RoundedCornerShape(16.dp))
                                    )

                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    //TODO
                },
                elevation = ButtonDefaults.buttonElevation(25.dp, 10.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF122333)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.5.dp, Color(0XFF39B54A))
            ) {
                Text(
                    text = " کام مکمل ہو گیا ہے۔",
                    fontFamily = jameelNooriFont,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp
                )
            }


        }


        BottomLogo()

    }

}


@Preview
@Composable
fun FinalPicturesScreenPreview() {
    FinalPicturesScreen()
}
