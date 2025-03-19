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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.rounded.Cancel
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun picturesFunctionality() {
    val context = LocalContext.current
    var initiallistCompleted by remember { mutableStateOf(false) }
    var initiallistOfImages = remember { mutableStateListOf<Bitmap?>(null, null) }

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
                fontSize = 18.sp,textAlign = TextAlign.End
            )
        } else initiallistCompleted = true

    }
    if (initiallistCompleted) {
        Row (modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
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
            IconButton(onClick = {/*Ab yaha pr agr usne cross pr click kra to ham step 2 pr chale jaenge*/}) {
                Icon(
                    imageVector = Icons.Rounded.Cancel,
                    contentDescription = "Done Button",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(100.dp)

                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "دونوں تصاویر دوبارہ لینا چاہتے ہیں؟",
                fontFamily = jameelNooriFont,
                fontSize = 20.sp
            )

        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(initiallistOfImages.size) { index ->
                initiallistOfImages[index]?.let { bitmap ->
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
