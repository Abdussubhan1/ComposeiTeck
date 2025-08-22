package com.itecknologi.itecktestingcompose.appScreens

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.itecknologi.itecktestingcompose.R
import com.itecknologi.itecktestingcompose.apiFunctions.StatisticsResponse
import com.itecknologi.itecktestingcompose.apiFunctions.getStatsOfLast7Days
import com.itecknologi.itecktestingcompose.appPrefs.PreferenceManager
import com.itecknologi.itecktestingcompose.functions.BottomLogo
import com.itecknologi.itecktestingcompose.functions.HandleDoubleBackToExit
import com.itecknologi.itecktestingcompose.functions.isInternetAvailable
import com.itecknologi.itecktestingcompose.functions.openAppNotificationSettings
import com.itecknologi.itecktestingcompose.functions.resetAllData
import kotlinx.coroutines.delay


@Composable
fun MenuScreen(context: Context, navController: NavHostController, prefs: PreferenceManager) {
    val name = prefs.getTechnicianName()
    var isLoggingOut by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isLoggingOut) 0f else 1f,
        animationSpec = tween(durationMillis = 500),
        label = "logoutAnimation"
    )
    val hasNewNotification =
        remember { mutableStateOf(prefs.getHasNewNotification()) }
    var duration by remember { mutableIntStateOf(0) }
    var statsResult by remember { mutableStateOf(StatisticsResponse(isLoading = true, 0, 0, 0, 0)) }
    var retryCount by remember { mutableIntStateOf(0) }
    HandleDoubleBackToExit()

    LaunchedEffect(key1 = duration, key2 = retryCount) {
        delay(1000)
        statsResult = getStatsOfLast7Days(cnic = prefs.getUserCNIC(), duration)
        Log.d("statsResult", statsResult.toString())
    }

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
        Spacer(modifier = Modifier.height(5.dp))

        // Chart Section — This will now recompose on duration change
        when {
            statsResult.errorValue == 0 && !statsResult.isLoading -> {

                CardPieChart(
                    duration,
                    OnDurationChange = { newDuration ->
                        duration = newDuration
                        statsResult.isLoading = true
                    },
                    statistics = statsResult
                )


            }
            statsResult.errorValue == 1 && !statsResult.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            "No Statistics Available!",
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh",
                                tint = Color.DarkGray,
                                modifier = Modifier.clickable(
                                    true,
                                    onClick = {
                                        retryCount = retryCount + 1
                                        if (!isInternetAvailable(context)) {
                                            Toast.makeText(
                                                context,
                                                "No Internet",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    })
                            )
                        }

                    }

                }
            }
            statsResult.errorValue == 0 || statsResult.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            else -> {}
        }
        Spacer(modifier = Modifier.height(5.dp))
        MenuScreenCard(navController, context, hasNewNotification)
        BottomLogo()
    }
}

@Composable
fun MenuScreenCard(
    navController: NavHostController,
    context: Context,
    hasNewNotification: MutableState<Boolean>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(4) { index ->
                MenuItemCard(
                    title = when (index) {
                        0 -> "Tasks"
                        1 -> "Notifications"
                        2 -> "Settings"
                        3 -> "Exit"
                        else -> ""
                    },
                    icon = when (index) {
                        0 -> Icons.Filled.Checklist
                        1 -> Icons.Default.Notifications
                        2 -> Icons.Default.Settings
                        3 -> Icons.Default.ExitToApp
                        else -> Icons.Default.Start
                    },
                    onClick = {
                        when (index) {
                            0 -> {
                                navController.navigate("New Installations Assigned Tasks Screen")
                            }

                            1 -> {
                                navController.navigate("NotificationScreen")
                                hasNewNotification.value = false

                            }

                            2 -> {
                                openAppNotificationSettings(context)
                            }

                            3 -> {
                                (context as? Activity)?.finish()
                            }
                        }
                    },
                    showDot = hasNewNotification,
                    index=index
                )
            }
        }
    }
}

@Composable
fun MenuItemCard(
    title: String, icon: ImageVector, onClick: () -> Unit,
    showDot: MutableState<Boolean>,
    index: Int
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E3D59) // Nice dark bluish tone
        ),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Makes it square
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .background(
                    Color(0xFF1E3D59)
                )
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(contentAlignment = Alignment.TopEnd) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                    if (index == 1 && showDot.value) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(Color.Yellow, shape = CircleShape)
                                .align(Alignment.TopEnd)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/*@Preview
@Composable
fun MenuScreenPreview() {
    MenuItemCard("Test", Icons.Default.PowerSettingsNew)
}*/


@Preview
@Composable
fun MenuPreview() {
    MenuScreen(
        context = LocalContext.current,
        rememberNavController(),
        PreferenceManager(LocalContext.current)
    )
}

