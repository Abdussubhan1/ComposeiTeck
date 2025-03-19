package com.example.itecktestingcompose

import android.Manifest
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun picturesFunctionality() {
    val context = LocalContext.current
    var listCompleted by remember { mutableStateOf(false) }
    var listOfImages = remember { mutableStateListOf<Bitmap?>(null, null, null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { result ->
        if (result != null) {
            val firstNullIndex = listOfImages.indexOfFirst { it == null }

            if (firstNullIndex != -1) {
                listOfImages[firstNullIndex] = result
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
        if (listOfImages[0] == null) {
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
        } else if (listOfImages[1] == null) {
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
        } else if (listOfImages[2] == null) {
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

        } else listCompleted = true


    }
    if (listCompleted) {
        Row {
            IconButton(onClick = { listOfImages.clear()
                listOfImages.addAll(listOf(null, null, null))
                listCompleted=false})
            {
                Icon(
                    imageVector = Icons.Rounded.Done,
                    contentDescription = "Done Button",
                    tint = Color.Green,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(start = 14.dp)
                )

            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "تمام تصاویر دوبارہ لینا چاہتے ہیں؟",
                fontFamily = jameelNooriFont,
                fontSize = 20.sp
            )

        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listOfImages.size) { index ->
                listOfImages[index]?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Captured Image $index",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp) // Adjust as needed
                            .padding(8.dp)
                    )
                }
            }
        }

    }
}
