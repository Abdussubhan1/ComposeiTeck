package com.example.itecktestingcompose

import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.itecktestingcompose.Constants.Constants
import kotlinx.coroutines.launch

@Composable
fun loginScreen(context: Context, navController: NavHostController? = null) {
    val version = getAppVersion(context)

    var validationResult by remember { mutableStateOf(CNICValidationResult(false, false)) }

    var cnic by remember { mutableStateOf("") }
    val couroutineScope = rememberCoroutineScope()
    val regex = Regex("^[0-9]{5}-[0-9]{7}-[0-9]{1}\$")
    val keyboard=LocalSoftwareKeyboardController.current



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
            onValueChange = { cnic=it.filter { it.isDigit() ||it=='-'} },

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
                .padding(horizontal = 32.dp).imePadding(),
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


                    if (keyboard != null) {
                        keyboard.hide()
                    }

                    if (cnic.isEmpty()||!regex.matches(cnic)){
                        cnic=""
                        Toast.makeText(
                        context,
                        "Please enter valid CNIC (XXXXX-XXXXXXX-X)",
                        Toast.LENGTH_SHORT
                    ).show()}

                    else {
                        validationResult = CNICValidationResult(false, true) // Updating the state for Loader

                        couroutineScope.launch {
                            validationResult = validateCnic(cnic.replace("-",""))
                            cnic=""
                            if (validationResult.ifUserExist) {
                                Toast.makeText(
                                    context,
                                    "Welcome ${Constants.name}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                if (navController != null) {
                                    navController.navigate("mainscreen")
                                }


                            } else {
                                Toast.makeText(context, "Technician Not Registered", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }

                },
            contentAlignment = Alignment.Center
        )

        {
            Image(
                painter = painterResource(id = R.drawable.check_double_line),
                contentDescription = "Check Icon",
            )
        }

        if(validationResult.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(25.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(24.dp),Color.Green
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


