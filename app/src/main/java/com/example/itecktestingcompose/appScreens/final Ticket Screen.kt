package com.example.itecktestingcompose.appScreens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.navigation.NavHostController
import com.example.itecktestingcompose.R
import com.example.itecktestingcompose.apiFunctions.StatusResult
import com.example.itecktestingcompose.apiFunctions.getStatus
import com.example.itecktestingcompose.apiFunctions.submitData
import com.example.itecktestingcompose.appPrefs.PreferenceManager
import com.example.itecktestingcompose.constants.Constants
import com.example.itecktestingcompose.functions.HandleDoubleBackToExit
import com.example.itecktestingcompose.functions.resetAllData
import com.example.itecktestingcompose.mainActivity.jameelNooriFont
import kotlinx.coroutines.launch

@Composable
fun finalTicket(navController: NavHostController, prefs: PreferenceManager, current: Context) {
    HandleDoubleBackToExit()

    var statusResult by remember {
        mutableStateOf(
            StatusResult(
                isLoading = true,
                GPSTime = "",
                GSMSignal = 0,
                Ignition = "",
                Location = "",
                PowerVoltages = ""
            )
        )
    }

    val allPictures = ArrayList<Bitmap?>()
    allPictures.addAll(Constants.initialPictures)
    allPictures.addAll(Constants.finalPictures)

    val couroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) { statusResult = getStatus(Constants.deviceID) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF122333))
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 50.dp),
            border = BorderStroke(1.dp, Color(0xFF90A4AE)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF102027))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Summary",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                val textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF263238),
                    textAlign = TextAlign.Center
                )
                if (statusResult.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(24.dp),
                        color = Color.Black
                    )
                } else {
                    listOf(
                        "Device Number:\n\n ${Constants.deviceID}",
                        "Location:\n\n ${statusResult.Location}",
                        "GPS Time:\n\n${statusResult.GPSTime}",
                        "Main Power:\n\n${statusResult.PowerVoltages}",
                        "GSM Signals:\n\n${statusResult.GSMSignal}",
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
                }

                Text(
                    text = "Captured Images",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = "Initial Pictures",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
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
                                    .border(
                                        1.dp, Color.LightGray, RoundedCornerShape(16.dp)
                                    )
                                    .shadow(4.dp, RoundedCornerShape(16.dp))
                            )


                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Final Pictures",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(Constants.finalPictures.size) { index ->
                        Constants.finalPictures[index]?.let { bitmap ->

                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "Captured Image $index",
                                modifier = Modifier
                                    .width(180.dp)
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .border(
                                        1.dp, Color.LightGray, RoundedCornerShape(16.dp)
                                    )
                                    .shadow(4.dp, RoundedCornerShape(16.dp))
                            )

                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        couroutineScope.launch {
                            val submitSuccess = submitData(
                                prefs.getUserCNIC(),
                                prefs.getTechnicianName(),
                                Constants.deviceID,
                                1,
                                prefs.getAppLoginID(),
                                allPictures,
                                Constants.vehicleID,
                                prefs.getTechnicianID(),
                                Constants.installedDeviceType,
                                Constants.immobilizer,
                                Constants.cust_Contact,
                                Constants.TLocID
                            )

                            if (submitSuccess) {

                                resetAllData() //Fun to reset RAM memory

                                navController.navigate("AllahHafizScreen") {
                                    popUpTo("finalPicturesScreen") { inclusive = true }
                                }

                                //Also showing notification when data is submitted successfully

                                val channelId = "installation"
                                val channelName = "Installation"

                                val notificationManager =
                                    current.getSystemService(NOTIFICATION_SERVICE) as NotificationManager


                                val importance = NotificationManager.IMPORTANCE_HIGH
                                val channel =
                                    NotificationChannel(channelId, channelName, importance)
                                notificationManager.createNotificationChannel(channel)

                                val notificationBuilder =
                                    NotificationCompat.Builder(current, channelId)
                                        .setSmallIcon(R.drawable.icon) // Make sure you have this icon in drawable
                                        .setContentTitle("Installation")
                                        .setContentText("Tracker installation success")
                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                        .setAutoCancel(true)

                                notificationManager.notify(0, notificationBuilder.build())


                            } else Toast.makeText(
                                current,
                                "Something went wrong!",
                                Toast.LENGTH_SHORT
                            ).show()
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
                        text = " کام مکمل ہو گیا ہے۔",
                        fontFamily = jameelNooriFont,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 17.sp
                    )
                }

            }
        }
    }


}