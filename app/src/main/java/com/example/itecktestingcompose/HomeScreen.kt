package com.example.itecktestingcompose

import android.content.Context
import android.Manifest
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun mainScreen(current: Context) {


    val couroutineScope = rememberCoroutineScope()
    var devID by remember { mutableStateOf("") }
    var validationResult by remember { mutableStateOf(DevValidationResult(false, false)) }
    val keyboard = LocalSoftwareKeyboardController.current
    var listCompleted by remember { mutableStateOf(false) }





    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .background(colorResource(R.color.white))
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.099f)
                .padding(top = 5.dp)
        )
        {
            Surface(color = Color.LightGray, modifier = Modifier.fillMaxSize()) {
                Row {
                    Image(
                        painter = painterResource(R.drawable.icon),
                        contentDescription = "",
                        modifier = Modifier.size(55.dp)
                    )

                    Column {
                        Text(
                            text = "خوش آمدید", fontFamily = jameelNooriFont,
                            modifier = Modifier.padding(start = 5.dp, top = 8.dp),
                            fontWeight = FontWeight.Bold, fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "Mr. ${Constants.name}!!",
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }
                }
            }


        }

        Row(
            modifier = Modifier.padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(

                imageVector = Icons.Rounded.Search,
                contentDescription = "Search Icon", tint = Color.Black,
                modifier = Modifier
                    .size(60.dp)
                    .padding(start = 10.dp, end = 10.dp)
                    .clickable {
                        keyboard?.hide()
                        if (devID.isEmpty() || devID.length < 7 || devID.length > 15) Toast.makeText(
                            current,
                            "Please enter valid device ID",
                            Toast.LENGTH_SHORT
                        ).show()
                        else {
                            validationResult = DevValidationResult(false, true)
                            couroutineScope.launch {
                                validationResult = validateDev(devID)

                                if (validationResult.ifDeviceExist)
                                    Toast.makeText(
                                        current,
                                        "Device ID is valid",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else {
                                    Toast.makeText(
                                        current,
                                        "Device Not found in Inventory",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    devID = ""
                                }
                            }
                        }
                    }
            )
            OutlinedTextField(
                value = devID,
                onValueChange = { devID = it.replace(" ", "").filter { it.isDigit() } },
                label = {
                    Text(
                        "ڈیوائس نمبر درج کریں۔",
                        fontFamily = jameelNooriFont,
                        fontSize = 20.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

        }
        if (validationResult.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(25.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(24.dp), Color.Green
            )
        }

        Spacer(modifier = Modifier.height(20.dp))



        if (validationResult.ifDeviceExist) {
            picturesFunctionality()
        }

    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    var context = LocalContext.current
    mainScreen(context)
}


//@Preview(showBackground = true)
@Composable
fun picturesFunctionality() {
    val context = LocalContext.current
    var listCompleted by remember { mutableStateOf(false) }
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }
    var listOfImages by remember { mutableStateOf<List<Bitmap>>(emptyList()) }
//    var selectedImage by remember { mutableStateOf<Bitmap?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { result ->
        if (result != null) {
            capturedImage = result // Save the captured image
            listOfImages = listOfImages + result // Add it to the list
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
        if (listOfImages.isEmpty()) {
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
            Text(
                text = "پہلی تصویر لینے کے لیے کلک کریں۔",
                modifier = Modifier.padding(end = 14.dp),
                fontFamily = jameelNooriFont,
                fontSize = 20.sp
            )
        }
        if (listOfImages.size == 1) {
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
            Text(
                text = "دوسری تصویر لینے کے لیے کلک کریں۔",
                modifier = Modifier.padding(end = 14.dp),
                fontFamily = jameelNooriFont,
                fontSize = 20.sp
            )
        }
        if (listOfImages.size == 2) {
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
            Text(

                text = "تیسری تصویر لینے کے لیے کلک کریں۔",
                modifier = Modifier.padding(end = 14.dp),
                fontFamily = jameelNooriFont,
                fontSize = 20.sp
            )


        }
        if (listOfImages.size == 3) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.purple_200))
            )

        }

    }

}


// Function to save image to storage
/*fun saveImageToStorage(context: Context, bitmap: Bitmap) {
    val filename = "CapturedImage_${System.currentTimeMillis()}.jpg"
    val fos: OutputStream?

    // Save to gallery
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }

    val imageUri = context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    )

    fos = imageUri?.let { context.contentResolver.openOutputStream(it) }

    fos?.use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show()
    }
}*/




