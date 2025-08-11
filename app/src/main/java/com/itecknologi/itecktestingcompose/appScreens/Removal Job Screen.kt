package com.itecknologi.itecktestingcompose.appScreens

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.itecknologi.itecktestingcompose.R
import com.itecknologi.itecktestingcompose.apiFunctions.getVehicleDetails
import com.itecknologi.itecktestingcompose.apiFunctions.getVehicleDetailsResponse
import com.itecknologi.itecktestingcompose.appPrefs.PreferenceManager
import com.itecknologi.itecktestingcompose.constants.Constants
import com.itecknologi.itecktestingcompose.functions.BottomLogo
import com.itecknologi.itecktestingcompose.functions.VehicleListScreen
import com.itecknologi.itecktestingcompose.functions.isInternetAvailable
import com.itecknologi.itecktestingcompose.functions.resetAllData
import com.itecknologi.itecktestingcompose.objects.vehicle_details
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun JobAssignedRemoval(
    context: Context,
    navController: NavHostController,
    prefs: PreferenceManager
) {
    val name = prefs.getTechnicianName()
        val hasNewNotification =
            remember { mutableStateOf(prefs.getHasNewNotification()) }
    var isLoggingOut by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isLoggingOut) 0f else 1f,
        animationSpec = tween(durationMillis = 500),
        label = "logoutAnimation"
    )
    var enableProceed by remember { mutableStateOf(false) }
    var response by remember {
        mutableStateOf(
            getVehicleDetailsResponse(
                success = false,
                message = ""
            )
        )
    }

    LaunchedEffect(Unit) {
        if (isInternetAvailable(context)) {
            while (true) {
                response = getVehicleDetails(
                    T_ID = prefs.getTechnicianID().toString(),
                    app_login_id = prefs.getAppLoginID()
                )
                if (response.success) {
                    break
                } else {
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }
                delay(2000)
            }
        }
        else
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show()

    }
    BackHandler { navController.navigate("Menu Screen") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF122333))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
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
                        text = name,
                        color = Color(0XFF39B54A),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(.6f),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                /*Box(
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
                }*/
                Spacer(modifier = Modifier.width(10.dp))
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
                    popUpTo("Menu Screen") { inclusive = true }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "Removal Tasks",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text("Select Vehicle for Removal", color = Color.White, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0XFF182b3c), shape = RoundedCornerShape(24.dp))
                .padding(8.dp)
        ) {
            Column {
                val coroutineScope = rememberCoroutineScope()
                var isRefreshing by remember { mutableStateOf(false) }
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing),
                    onRefresh = {
                        // Trigger refresh
                        isRefreshing = true

                        // Simulate network call
                        coroutineScope.launch {
                            if (isInternetAvailable(context)) {
                                response = getVehicleDetails(
                                    T_ID = prefs.getTechnicianID().toString(),
                                    app_login_id = prefs.getAppLoginID()
                                )
                                delay(2000)
                                isRefreshing = false
                            } else {
                                isRefreshing = false
                                Toast.makeText(
                                    context,
                                    "No Internet Connection",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }, content = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.80f)
                                .background(Color(0xFF122333), shape = RoundedCornerShape(24.dp))
                                .padding(8.dp)
                        ) {
                            if (!response.success) {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    item {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text("Loading..", color = Color.White, fontSize = 18.sp)
                                            Spacer(modifier = Modifier.height(18.dp))
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(32.dp),
                                                color = Color(0XFF39B54A)
                                            )
                                        }
                                    }

                                }

                            } else {
                                prefs.setHasNewNotification(value = false)
                                if (vehicle_details.dataList.isEmpty()) {
                                    LazyColumn(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        item {
                                            Column(
                                                modifier = Modifier.fillMaxSize(),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    "No Pending Task",
                                                    color = Color.White,
                                                    fontSize = 18.sp
                                                )
                                            }
                                        }
                                    }

                                } else {
                                    VehicleListScreen(
                                        vehicleList = vehicle_details.dataList,
                                        onConfirmSelection = { isSelected, vehicleID, vehicleEngine, vehicleChassis, vehicleMake, vehicleModel, assignedDate, vehicleVRN, xcordinate, ycordinate, jobAssignedID, customerContactNumber ->
                                            enableProceed = isSelected
                                            Constants.vehicleID =
                                                vehicleID
                                                    ?: "" //Yeh last get log wali api mein bhejna hai
                                            Constants.engineNumber = vehicleEngine
                                                ?: "" //Saving for selected card in device entry screen
                                            Constants.chassisNumber = vehicleChassis
                                                ?: "" //Saving for selected card in device entry screen
                                            Constants.make = vehicleMake
                                                ?: "" //Saving for selected card in device entry screen
                                            Constants.model = vehicleModel
                                                ?: "" //Saving for selected card in device entry screen
                                            Constants.JobAssigneddate = assignedDate
                                                ?: "" //Saving for selected card in device entry screen
                                            Constants.VRN = vehicleVRN
                                                ?: "" //Saving for selected card in device entry screen
                                            Constants.X = xcordinate
                                                ?: 0.0 //Saving for selected card in device entry screen
                                            Constants.Y = ycordinate
                                                ?: 0.0 //Saving for selected card in device entry screen
                                            Constants.technicalJobAssignedID = jobAssignedID
                                                ?: "" //Yeh last get log wali api mein bhejna hai
                                            Constants.cust_Contact = customerContactNumber
                                                ?: "" //Yeh last get log wali api mein bhejna hai
                                        },
                                        navController = navController,
                                        prefs = prefs
                                    )


                                }
                            }
                        }

                    }, swipeEnabled = true, refreshTriggerDistance = 80.dp
                )

                              /*  Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.80f)
                                        .background(
                                            Color(0xFF122333),
                                            shape = RoundedCornerShape(24.dp)
                                        )
                                        .padding(8.dp)
                                ) {
                                    if (!response.success) {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text("Loading..", color = Color.White, fontSize = 18.sp)
                                            Spacer(modifier = Modifier.height(18.dp))
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(32.dp),
                                                color = Color(0XFF39B54A)
                                            )
                                        }

                                    } else {
                                        if (vehicle_details.dataList.isEmpty()) {
                                            Column(
                                                modifier = Modifier.fillMaxSize(),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    "No Pending Tasks",
                                                    color = Color.White,
                                                    fontSize = 18.sp,
                                                )
                                            }
                                        } else {
                                            VehicleListScreen(
                                                vehicleList = vehicle_details.dataList,
                                                onConfirmSelection = { isSelected, vehicleID, vehicleEngine, vehicleChassis, vehicleMake, vehicleModel, assignedDate, vehicleVRN, xcordinate, ycordinate, jobAssignedID, customerContactNumber ->
                                                    enableProceed = isSelected
                                                    Constants.vehicleID =
                                                        vehicleID ?: "" //Yeh last get log wali api mein bhejna hai
                                                    Constants.engineNumber = vehicleEngine
                                                        ?: "" //Saving for selected card in device entry screen
                                                    Constants.chassisNumber = vehicleChassis
                                                        ?: "" //Saving for selected card in device entry screen
                                                    Constants.make = vehicleMake
                                                        ?: "" //Saving for selected card in device entry screen
                                                    Constants.model = vehicleModel
                                                        ?: "" //Saving for selected card in device entry screen
                                                    Constants.JobAssigneddate = assignedDate
                                                        ?: "" //Saving for selected card in device entry screen
                                                    Constants.VRN = vehicleVRN
                                                        ?: "" //Saving for selected card in device entry screen
                                                    Constants.X = xcordinate
                                                        ?: 0.0 //Saving for selected card in device entry screen
                                                    Constants.Y = ycordinate
                                                        ?: 0.0 //Saving for selected card in device entry screen
                                                    Constants.technicalJobAssignedID = jobAssignedID
                                                        ?: "" //Yeh last get log wali api mein bhejna hai
                                                    Constants.cust_Contact = customerContactNumber
                                                        ?: "" //Yeh last get log wali api mein bhejna hai
                                                }
                                            )
                                        }

                                    }

                                }*/
            }


        }
        Spacer(modifier = Modifier.height(12.dp))
        // Button for Proceed
        Button(
            onClick = {

                navController.navigate("mainscreen")
            },
            enabled = enableProceed,
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
                text = "Proceed",
                fontWeight = FontWeight.SemiBold
            )
        }


        BottomLogo()
    }
}

@Preview
@Composable
fun JobAssignedRemovalScreen() {
    JobAssignedRemoval(
        context = LocalContext.current,
        rememberNavController(),
        PreferenceManager(LocalContext.current)
    )
}
