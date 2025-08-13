package com.itecknologi.itecktestingcompose.appScreens


import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.itecknologi.itecktestingcompose.R
import com.itecknologi.itecktestingcompose.apiFunctions.notificationHistory
import com.itecknologi.itecktestingcompose.modelClasses.NotificationHistoryItem
import com.itecknologi.itecktestingcompose.appPrefs.PreferenceManager
import com.itecknologi.itecktestingcompose.functions.BottomLogo


@Composable
fun NotificationScreen(navController: NavHostController, prefs: PreferenceManager) {
    var notifications by remember { mutableStateOf<List<NotificationHistoryItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    prefs.setHasNewNotification(value = false)

    LaunchedEffect(Unit) {
        notifications = notificationHistory(prefs.getUserCNIC())
        isLoading = false

    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF122333))
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(14.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            // Centered text
            Text(
                text = "Notifications",
                color = Color.White,
                fontSize = 28.sp,
                textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center)
            )

            // Icon at the end (right)
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.Red, shape = CircleShape)
                        .padding(6.dp).size(20.dp) // Optional padding inside red circle
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }
        }

/*        Spacer(modifier = Modifier.height(8.dp))*/

        if (isLoading) {
            Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(25.dp)
                        .size(24.dp),
                    color = Color(0XFF39B54A)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                LazyColumn {
                    items(notifications) { item ->
                        NotificationCard(item)
                    }
                }
            }
        }
    }
}


@Composable
fun NotificationCard(item: NotificationHistoryItem) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(start = 5.dp)) {
                Spacer(modifier = Modifier.height(7.dp))
                if (item.title?.contains("Assigned") == true) {
                    Image(
                        painter = painterResource(id = R.drawable.assignment),
                        contentDescription = "Notification",
                        modifier = Modifier.size(30.dp),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(Color.Green)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.dismiss),
                        contentDescription = "Notification",
                        modifier = Modifier.size(30.dp),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(Color.Red)
                    )
                }

            }



            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = item.title ?: "",
                    color = if (item.title?.contains("Unassigned") == true) Color.Red else Color.Green,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 14.dp)
                )
                Text(
                    text = item.body ?: "",
                    maxLines = 5,
                    fontStyle = FontStyle.Italic,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )
                Text(
                    text = item.entrydate ?: "",
                    textAlign = TextAlign.End,
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(28.dp))
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White,
                    thickness = 1.25.dp
                )
            }
        }

    }
}

@Preview
@Composable
fun hsadgsad() {
    NotificationScreen(rememberNavController(), PreferenceManager(LocalContext.current))
}


