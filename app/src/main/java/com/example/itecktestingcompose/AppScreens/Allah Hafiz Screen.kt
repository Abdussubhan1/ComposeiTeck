package com.example.itecktestingcompose.AppScreens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.itecktestingcompose.functions.HandleDoubleBackToExit


@Composable
fun AllahHafiz(navController: NavHostController) {
    HandleDoubleBackToExit()
    val scale = remember { Animatable(0f) }

//For lottie animation

    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("complete_lottie.json")
    )
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )


    // Animate scaling up
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 3000, easing = FastOutSlowInEasing)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF122333)) // Dark blue background
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )


        Text(
            text = "Shukriya!  🙏",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.graphicsLayer(scaleX = scale.value, scaleY = scale.value)
        )
//
//        Spacer(modifier = Modifier.height(48.dp))
//
//        // Sub text
//        Text(
//            text = "Installation ka data kamiyabi se submit hogaya hai.\n\nAllah Hafiz ❤️",
//            color = Color(0xFFDDDDDD),
//            fontSize = 18.sp,
//            textAlign = TextAlign.Center
//        )
//
        Spacer(modifier = Modifier.height(40.dp))


        Button(
            onClick = {
                navController.navigate("mainscreen") {
                    popUpTo("AllahHafizScreen") {
                        inclusive = true
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Home   \uD83C\uDFE0", color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}


@Preview
@Composable
fun PreviewAllahHafiz() {
    AllahHafiz(rememberNavController())
}