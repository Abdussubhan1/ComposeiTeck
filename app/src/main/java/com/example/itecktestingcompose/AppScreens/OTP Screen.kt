package com.example.itecktestingcompose.AppScreens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.R
import com.example.itecktestingcompose.appPrefs.PreferenceManager

@Composable
fun OTPScreen(context: Context, navController: NavHostController, prefs: PreferenceManager) {


    var otp by remember { mutableStateOf("") }
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF122333))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(130.dp))

        Image(
            painter = painterResource(id = R.drawable.img_nic_3),
            contentDescription = "CNIC Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(width = 356.dp, height = 150.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = otp,
            onValueChange = { otp = it },
            placeholder = {
                Text(
                    "Enter OTP",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 20.sp
                )
            },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0XFFD9D9D9),
                unfocusedContainerColor = Color(0XFFD9D9D9),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0XFF122333)
            ),
            shape = RoundedCornerShape(80.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .width(200.dp)
                .height(42.dp)
                .padding(horizontal = 32.dp)
                .background(
                    if (otp == "" || otp.length < 6 || otp.length > 6) Color.Gray else Color(
                        0XFF39B54A
                    ), shape = RoundedCornerShape(17.dp)
                )
                .clickable(enabled = otp != "" && otp.length == 6) {
                    keyboard?.hide()//hide the keyboard
                    if (otp == Constants.otp) {

                        prefs.setUserCNIC(cnic = Constants.cnic)

                        navController.navigate("mainscreen") {
                            popUpTo("OTP Screen") {
                                inclusive = true
                            }
                        }
                        Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT)
                            .show()
                    }

                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.check_double_line),
                contentDescription = "Check Icon"
            )

        }

        Spacer(modifier = Modifier.weight(1f))

        BottomLogo()
    }
}

@Preview
@Composable
fun OTPScreenPreview() {
    OTPScreen(context = LocalContext.current, rememberNavController(), PreferenceManager(LocalContext.current))
}