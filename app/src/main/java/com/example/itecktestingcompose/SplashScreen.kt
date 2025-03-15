package com.example.itecktestingcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun splashScreen(navController: NavHostController) {

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1000) // Wait for 1 second
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.gear_icon),
                contentDescription = "Gear Logo",
                modifier = Modifier
                    .size(100.dp)
            )

            Text(
                text = "Testing App",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF003366), // Dark blue color
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(0.7f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "Company Icon",
                modifier = Modifier.size(40.dp)
            )

            Text(
                text = "By iTecknologi",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF003366), // Dark blue color
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }


}