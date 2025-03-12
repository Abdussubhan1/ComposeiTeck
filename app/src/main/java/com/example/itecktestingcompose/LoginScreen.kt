package com.example.itecktestingcompose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun loginScreen(
    context: Context,
    onClick: (String) -> Unit
) {

    val version = getAppVersion(context)
    var cnic by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Image(
            painter = painterResource(id = R.drawable.img_nic_2),
            contentDescription = "CNIC Image",
            modifier = Modifier
                .height(200.dp)
                .size(300.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        OutlinedTextField(
            value = cnic,
            onValueChange = { cnic = it.filter { char -> char.isDigit() || char == '-' } },
            label = {
                Text(
                    "اپنا شناختی کارڈ نمبر درج کریں",
                    textAlign = TextAlign.End,
                    fontFamily = jameelNooriFont,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 26.sp
                )
            },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        )
        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Box(
            modifier = Modifier
                .width(200.dp)
                .height(42.dp)
                .padding(horizontal = 32.dp)
                .background(Color(0xFF008000), shape = RoundedCornerShape(10.dp))
                .clickable {
                    onClick(cnic)
//                    if (cnic.isEmpty()) Toast.makeText(
//                        context,
//                        "Please enter CNIC",
//                        Toast.LENGTH_SHORT
//                    ).show()
////                    val cnicPattern = Regex("^\\d{5}-\\d{7}-\\d{1}$") // CNIC pattern
//
////                    when {
////                        cnic.isEmpty() -> {
////                            Toast.makeText(context, "Please enter CNIC", Toast.LENGTH_SHORT).show()
////                        }
//////                        !cnicPattern.matches(cnic) -> {
//////                            Toast.makeText(
//////                                context,
//////                                "Please enter a valid CNIC (XXXXX-XXXXXXX-X)",
//////                                Toast.LENGTH_SHORT
//////                            ).show()
//////                        }
//                    else {
//
//
////                        GlobalScope.launch {
////
////                            if (validateCnic(cnic.replace("-", "")))
////                                if (navController != null)
////                                    navController.navigate("mainscreen")
////                                 else {
////                                    Toast.makeText(
////                                        context,
////                                        "User does not exist", Toast.LENGTH_SHORT
////                                    ).show()
////                                }
////                        }
//
//                    }

                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.check_double_line),
                contentDescription = "Check Icon",
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "iTeck Icon",
                modifier = Modifier.size(40.dp)
            )

            Text(
                text = "By iTecknologi",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00008B)
            )

            Text(
                text = "Version: $version",
                fontSize = 12.sp,
                color = Color(0xFFA8A8A8)
            )
        }
    }

}

@Composable
fun checkButton() {
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(42.dp)
            .padding(end = 32.dp)
            .background(Color(0xFF008000), shape = RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.check_double_line),
            contentDescription = "Check Icon",
        )
    }
}