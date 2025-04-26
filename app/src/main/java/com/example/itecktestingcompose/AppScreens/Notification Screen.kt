package com.example.itecktestingcompose.AppScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.itecktestingcompose.APIFunctions.notificationHistory
import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.Mainactivity.jameelNooriFont
import com.example.itecktestingcompose.ModelClasses.NotificationHistoryItem
import kotlinx.coroutines.launch


@Composable
fun NotificationScreen(navController: NavHostController) {
    var notifications by remember { mutableStateOf<List<NotificationHistoryItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        notifications = notificationHistory(Constants.cnic)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF122333))
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Box(
                    modifier = Modifier.background(
                        Color.Red,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Notifications",
            color = Color.White,
            fontSize = 28.sp,
            textDecoration = TextDecoration.Underline,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


            LazyColumn {
                items(notifications) { item ->
                    NotificationCard(item)
                }
            }

    }
}
@Composable
fun NotificationCard(item: NotificationHistoryItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A2B3C))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.title, color = Color.White, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.body, color = Color.LightGray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Date: ${item.entrydate.date}", color = Color.Gray, fontSize = 12.sp)
        }
    }
}




@Preview
@Composable
fun NotificationScreenPreview() {
    NotificationScreen(rememberNavController())
}