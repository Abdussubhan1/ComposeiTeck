package com.example.itecktestingcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.ModelClasses.Ignition
import kotlinx.coroutines.launch

@Composable
fun TestingPage(navController: NavHostController) {

    var showDialogue by remember { mutableStateOf(false) }
    HandleDoubleBackToExit()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF122333)) // Dark blue background
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.user_smile_fill), // Add smile icon
                    contentDescription = null,
                    tint = Color.Green,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Khush Amdeed!", color = Color.White, fontSize = 12.sp)
                    Text(
                        Constants.name,
                        color = Color.Green,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = "Tips",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )


        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0XFF182b3c), shape = RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        CustomTextField_screen2(
                            Constants.deviceID
                        ) {}

                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF00C853)) // Green search
                            .clickable(enabled = false) {

                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                }


            }

        }
        Spacer(modifier = Modifier.height(32.dp))

        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0XFF182b3c), shape = RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            ValidationStatusUI()
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                showDialogue = true
            },
            elevation = ButtonDefaults.buttonElevation(25.dp, 10.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF122333)),
            modifier = Modifier
                .fillMaxWidth(), shape = RoundedCornerShape(50)
        ) {
            Text(
                text = " ڈیوائس کو تبدیل کریں اور انسٹالیشن کو دہرائیں۔",
                fontFamily = jameelNooriFont,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 21.sp
            )
        }
        if (showDialogue) {
            Alert(
                title = "ٹریکر کی تبدیلی",
                text = "کیا آپ واقعی ٹریکر تبدیل کرنا چاہتے ہیں؟",
                onDismiss = { showDialogue = false },
                navController = navController
            )

        }

        BottomLogo()

    }


}


@Composable
fun ValidationStatusUI() {
    val couroutineScope = rememberCoroutineScope()
    var battery by remember { mutableStateOf("") }
    var ignition by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0XFF182b3c)) // Dark blue background
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            StatusRow("Location", Color.Red, onRefresh = {})

            StatusRow(
                "Battery",
                if (battery == "Disconnected") Color.Red else Color(0xFF00C853),
                onRefresh = {
                    couroutineScope.launch {
                        battery = validateBattery(Constants.deviceID)
                    }
                })

            StatusRow("Ignition",if (ignition == "OFF") Color.Red else Color(0xFF00C853), onRefresh = {couroutineScope.launch {
                ignition = validateIgnition(Constants.deviceID)
            }})
            StatusRow("Relay", Color.Red, onRefresh = {})}
        }

    }
}

@Composable
fun CustomTextField_screen2(
    value: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color(0XFF000000),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Preview(showBackground = true)
@Composable
fun TestingPagePreview() {
    TestingPage(rememberNavController())
}

@Composable
fun Alert(
    title: String,
    text: String,
    onDismiss: () -> Unit,
    navController: NavHostController
) {
    AlertDialog(
        title = {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                fontFamily = jameelNooriFont,
                textAlign = TextAlign.End
            )
        },
        text = {
            Text(
                text = text,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                fontFamily = jameelNooriFont,
                textAlign = TextAlign.End
            )
        },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                navController.navigate("mainscreen")
            }, elevation = ButtonDefaults.buttonElevation(25.dp, 10.dp)) {
                Text("جی ہاں", color = Color.White, fontFamily = jameelNooriFont)
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismiss() },
                elevation = ButtonDefaults.buttonElevation(25.dp, 10.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text("نہیں", color = Color.White, fontFamily = jameelNooriFont)
            }
        }, containerColor = Color(0xFF122333), titleContentColor = Color.White

    )
}


@Composable
fun StatusRow(
    label: String,
    statusColor: Color,
    onRefresh: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            label,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .weight(0.2f)
        )

        LinearProgressIndicator(
            progress = { 1f },
            color = statusColor,
            modifier = Modifier
                .weight(0.45f)
                .height(10.dp)
                .clip(RoundedCornerShape(50))
        )

        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Refresh",
            tint = Color.White,
            modifier = Modifier
                .weight(0.2f)
                .size(24.dp)
                .clickable { onRefresh() }
        )
    }
}

