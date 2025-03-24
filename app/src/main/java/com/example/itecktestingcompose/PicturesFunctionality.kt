package com.example.itecktestingcompose

import android.Manifest
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.rounded.BatteryAlert
import androidx.compose.material.icons.rounded.BatteryChargingFull
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.RestorePage
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.itecktestingcompose.Constants.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun picturesFunctionality(
    devID: String,
    initiallistOfImages: SnapshotStateList<Bitmap?>,
    locValidationResult: LocValidationResult,
    couroutineScope: CoroutineScope,
    navController: NavHostController
) {

    val context = LocalContext.current
    var initiallistCompleted by remember { mutableStateOf(false) }
    var moveToTesting by remember { mutableStateOf(false) }


    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { result ->
        if (result != null) {
            val firstNullIndex = initiallistOfImages.indexOfFirst { it == null }

            if (firstNullIndex != -1) {
                initiallistOfImages[firstNullIndex] = result
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

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (initiallistOfImages[0] == null) {
            Icon(
                imageVector = Icons.Default.CameraEnhance,
                contentDescription = "Camera Icon 1",
                tint = colorResource(R.color.teal_200),
                modifier = Modifier
                    .size(60.dp)
                    .padding(start = 14.dp)
                    .clickable {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = "ڈیوائس کی تنصیب سے پہلے, پہلی تصویر لینے کے لیے کلک کریں",
                modifier = Modifier.padding(end = 14.dp),
                fontFamily = jameelNooriFont,
                maxLines = 2,
                fontSize = 18.sp, textAlign = TextAlign.End
            )
        } else if (initiallistOfImages[1] == null) {
            Icon(
                imageVector = Icons.Default.CameraEnhance,
                contentDescription = "Camera Icon 1",
                tint = colorResource(R.color.teal_200),
                modifier = Modifier
                    .size(60.dp)
                    .padding(start = 14.dp)
                    .clickable {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = "ڈیوائس کی تنصیب سے پہلے دوسری تصویر لینے کے لیے کلک کریں۔",
                modifier = Modifier.padding(end = 14.dp),
                fontFamily = jameelNooriFont,
                fontSize = 18.sp, textAlign = TextAlign.End
            )
        } else
            initiallistCompleted = true

    }

    if (initiallistCompleted && !moveToTesting) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = {
                initiallistOfImages.clear()
                initiallistOfImages.addAll(listOf(null, null))
                initiallistCompleted = false
            })
            {
                Icon(
                    imageVector = Icons.Rounded.Done,
                    contentDescription = "Done Button",
                    tint = Color.Green,
                    modifier = Modifier
                        .size(120.dp)

                )

            }


            IconButton(onClick = {
                moveToTesting = true
            }

            ) {
                Icon(
                    imageVector = Icons.Rounded.Cancel,
                    contentDescription = "Cross Button",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(120.dp)

                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "دونوں تصاویر دوبارہ لینا چاہتے ہیں؟",
                fontFamily = jameelNooriFont,
                fontSize = 20.sp
            )


        }

    }

    if (!moveToTesting) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(initiallistOfImages.size) { index ->
                initiallistOfImages[index]?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Captured Images",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp) // Adjust as needed
                            .padding(8.dp)
                    )
                }
            }
        }
    }
    if (moveToTesting) {
        moveToTestingForm(devID)
        devLocation(
            locValidationResult,
            couroutineScope,
            devID,
            navController
        )

    }
}


@Composable
fun moveToTestingForm(devID: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Technician Name: ${Constants.name} ")
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Device Number: $devID")

    }

}

@Composable
fun devLocation(
    locValidationResult: LocValidationResult,
    couroutineScope: CoroutineScope,
    devID: String,
    navController: NavHostController
) {
    var battery by remember { mutableStateOf("") }
    var context = LocalContext.current
    var showDialogue by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Vehicle Location")
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(14.dp))
                    .background(
                        colorResource(R.color.purple_200)
                    )
                    .border(3.dp, color = Color.Black, shape = RoundedCornerShape(14.dp))
                    .padding(8.dp),
            )
            {
                Text(text = "${locValidationResult.latitude},${locValidationResult.longitude}")
            }

            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),

                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "Battery: $battery")
                    if (battery == "Connected") {
                        Icon(
                            imageVector = Icons.Rounded.BatteryChargingFull,
                            contentDescription = "Battery Full Icon",
                            tint = Color.Green,
                            modifier = Modifier.size(35.dp)
                        )
                        Text(text = "+ 12V ")
                    } else if (battery == "Disconnected") {
                        Icon(
                            imageVector = Icons.Rounded.BatteryAlert,
                            contentDescription = "Battery Full Icon",
                            tint = Color.Red,
                            modifier = Modifier.size(35.dp)
                        )
                        Text(text = " 0V ")
                    }

                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Icon(
                imageVector = Icons.Rounded.Refresh,
                contentDescription = "Refresh Icon",
                tint = Color.Black,
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        couroutineScope.launch {
                            locValidationResult.latitude = validateLoc(devID).latitude
                            locValidationResult.longitude = validateLoc(devID).longitude
                            battery = validateBattery(devID)
                            Toast.makeText(
                                context,
                                "Refreshed!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            )


            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    showDialogue = true

                }, modifier = Modifier
                    .padding(bottom = 14.dp)
                    .background(Color.LightGray)
            ) {
                reset()
            }
        }
    }
    if (showDialogue) {
        AlertDialog(
            title = { Text(text = "Change Tracker") },
            text = { Text(text = "Are you sure you want to change tracker?") },
            onDismissRequest = { showDialogue = false },
            confirmButton = {
                TextButton(onClick = {
                    navController.navigate("mainscreen")
                    showDialogue = false
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialogue = false }) {
                    Text("No")
                }
            }
        )
    }

}

@Preview(showBackground = false)
@Composable
fun reset() {
    Icon(
        imageVector = Icons.Rounded.RestorePage,
        contentDescription = "reattempt", tint = Color.White,
        modifier = Modifier.size(50.dp)
    )
}


@Preview
@Composable
fun PreviewDevLocation() {

    devLocation(
        LocValidationResult(22.22222, 23.22222),
        rememberCoroutineScope(),
        "123456789",
        NavHostController(LocalContext.current)
    )

}