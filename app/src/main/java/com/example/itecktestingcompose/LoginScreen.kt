package com.example.itecktestingcompose

import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.itecktestingcompose.Constants.Constants
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(context: Context, navController: NavHostController? = null) {

    HandleDoubleBackToExit()

    var validationResult by remember {
        mutableStateOf(
            CNICValidationResult(
                ifUserExist = false,
                isLoading = false
            )
        )
    }

    var cnic by remember { mutableStateOf("") }
    val couroutineScope = rememberCoroutineScope()
    val regex = Regex("^[0-9]{5}-[0-9]{7}-[0-9]$")
    val keyboard = LocalSoftwareKeyboardController.current
    val version = remember { getAppVersion(context) }

    Log.d("LoginScreen", "checkVersion: $version")



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF122333)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(150.dp))

        Image(
            painter = painterResource(id = R.drawable.img_nic_3),
            contentDescription = "CNIC Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(width = 356.dp, height = 150.dp)
        )


        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = cnic,
            onValueChange = { cnic = it.filter { it.isDigit() || it == '-' } },

            placeholder = {
                Text(
                    "اپنا شناختی کارڈ نمبر درج کریں",
                    textAlign = TextAlign.End,
                    fontFamily = jameelNooriFont,
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
        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Box(
            modifier = Modifier
                .width(200.dp)
                .height(42.dp)
                .padding(horizontal = 32.dp)
                .background(Color(0XFF39B54A), shape = RoundedCornerShape(10.dp))
                .clickable(enabled = !validationResult.isLoading) {


                    if (keyboard != null) {
                        keyboard.hide()
                    }

                    if (cnic.isEmpty() || !regex.matches(cnic)) {
                        cnic = ""
                        Toast.makeText(
                            context,
                            "Please enter valid CNIC (XXXXX-XXXXXXX-X)",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        validationResult =
                            CNICValidationResult(
                                ifUserExist = false,
                                isLoading = true
                            ) // Updating the state for Loader

                        couroutineScope.launch {
                            validationResult = validateCnic(cnic.replace("-", ""))
                            cnic = ""
                            if (validationResult.ifUserExist) {
                                Toast.makeText(
                                    context,
                                    "Welcome ${Constants.name}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                if (navController != null) {
                                    navController.navigate("mainscreen") {
                                        popUpTo("login") {
                                            inclusive = true
                                        }
                                    }
                                }


                            } else {
                                Toast.makeText(
                                    context,
                                    "Technician Not Registered",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }

                },
            contentAlignment = Alignment.Center
        )

        {
            if (!validationResult.isLoading)
                Image(
                    painter = painterResource(id = R.drawable.check_double_line),
                    contentDescription = "Check Icon",
                ) else
                Text("Loading...", color = Color.Black, fontSize = 20.sp)
        }

        if (validationResult.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(25.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(24.dp), Color(0XFF39B54A)
            )
        }

        Spacer(modifier = Modifier.weight(1f))



        BottomLogo()



    }


}

@Preview
@Composable
fun LoginPreview() {
    LoginScreen(context = LocalContext.current, rememberNavController())
}


