package com.itecknologi.itecktestingcompose.appScreens

import android.Manifest
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.itecknologi.itecktestingcompose.constants.Constants
import com.itecknologi.itecktestingcompose.mainActivity.jameelNooriFont
import com.itecknologi.itecktestingcompose.R
import com.itecknologi.itecktestingcompose.appPrefs.PreferenceManager
import com.itecknologi.itecktestingcompose.functions.HandleDoubleBackToExit
import com.itecknologi.itecktestingcompose.functions.resetAllData
import kotlinx.coroutines.delay


@Composable
fun initialPicTake(context: Context, navController: NavHostController, prefs: PreferenceManager) {

    HandleDoubleBackToExit()
    val name = prefs.getTechnicianName()

    var isLoggingOut by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isLoggingOut) 0f else 1f,
        animationSpec = tween(durationMillis = 500),
        label = "logoutAnimation"
    )

    var initiallistCompleted by remember { mutableStateOf(false) }
    var initiallistOfImages = remember { mutableStateListOf<Bitmap?>(null, null) }
    var moveToTesting by remember { mutableStateOf(false) }


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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF122333)) // Dark blue background
            .padding(horizontal = 16.dp).verticalScroll(rememberScrollState()),
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
                                resetAllData()
                            }
                    )
                }

            }

        }

        if (isLoggingOut) {
            LaunchedEffect(true) {
                delay(500) // Wait for animation to finish
                prefs.setUserCNIC(cnic = "")
                prefs.setTechnicianName(name = "")
                prefs.setAppLoginID(id = "")
                prefs.setTechnicianID(T_ID=0)
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
                        CustomTextField_screen2(
                            Constants.deviceID
                        ) {}

                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0XFF39B54A))// Green search
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
            contentPadding = PaddingValues(horizontal = 8.dp) // No extra side padding
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
                        popUpTo("initialPicturesScreen") {inclusive=true}
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

@Preview
@Composable
fun initialPicTakePreview() {
    initialPicTake(LocalContext.current, rememberNavController(), PreferenceManager(LocalContext.current))
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
