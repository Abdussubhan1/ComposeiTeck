package com.example.itecktestingcompose

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.itecktestingcompose.Constants.Constants
import com.example.itecktestingcompose.DataBase.AppDatabase
import com.example.itecktestingcompose.DataBase.DeviceSearchHistory
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun mainScreen(current: Context, navController: NavHostController) {

    val couroutineScope = rememberCoroutineScope()
    var isEnabled by remember { mutableStateOf(true) }
    var devID by remember { mutableStateOf("") }
    var device by remember { mutableStateOf("") }
    var validationResult by remember { mutableStateOf(DevValidationResult(false, false)) }
    var locValidationResult = remember { mutableStateOf(LocValidationResult(0.0, 0.0)) }
    val keyboard = LocalSoftwareKeyboardController.current
    var initiallistOfImages = remember { mutableStateListOf<Bitmap?>(null, null) }

    val db = AppDatabase.AppDatabaseInstance.getDatabase(current)
    val dao = db.getHistory()





    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .background(colorResource(R.color.white))
    ) {

        Card(
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            Surface(color = Color.LightGray, modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.icon),
                        contentDescription = "",
                        modifier = Modifier.size(55.dp)
                    )

                    Column {
                        Text(
                            text = "خوش آمدید", fontFamily = jameelNooriFont,
                            modifier = Modifier.padding(start = 5.dp, top = 8.dp),
                            fontWeight = FontWeight.Bold, fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "Mr. ${Constants.name}!!",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }
                }
            }


        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.History,
                contentDescription = "History Icon",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        navController.navigate("historyscreen")
                    },
                tint = Color.Blue
            )
            Spacer(modifier = Modifier.width(5.dp))
        }


        Row(

            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(

                imageVector = Icons.Rounded.Search,
                contentDescription = "Search Icon", tint = Color.Black,
                modifier = Modifier
                    .size(60.dp)
                    .padding(start = 10.dp, end = 10.dp)
                    .clickable(enabled = isEnabled) {
                        keyboard?.hide()
                        if (devID.isEmpty() || devID.length < 7 || devID.length > 15) {

                            couroutineScope.launch {
                                if (devID.isNotEmpty()) {
                                    dao.insert(DeviceSearchHistory(0, devID))
                                }
                            }

                            Toast.makeText(
                                current,
                                "Please enter valid device ID",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            validationResult = DevValidationResult(false, true)
                            couroutineScope.launch {

                                dao.insert(DeviceSearchHistory(0, deviceNumber = devID))

                                validationResult = validateDev(devID)
                                locValidationResult.value = validateLoc(devID)

                                if (validationResult.ifDeviceExist) {
                                    isEnabled = false
                                    device = devID
                                    devID = ""
                                    Toast.makeText(
                                        current,
                                        "Device ID is valid",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        current,
                                        "Device Not found in Inventory",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    devID = ""
                                }
                            }
                        }
                    }

            )
            OutlinedTextField(
                value = devID,
                onValueChange = { devID = it.replace(" ", "").filter { it.isDigit() } },
                label = {
                    Text(
                        "ڈیوائس نمبر درج کریں۔",
                        fontFamily = jameelNooriFont,
                        fontSize = 20.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }, enabled = isEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

        }
        if (validationResult.isLoading) {

            CircularProgressIndicator(
                modifier = Modifier
                    .padding(25.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(24.dp), Color.Green
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        if (validationResult.ifDeviceExist) {
            picturesFunctionality(
                device,
                initiallistOfImages = initiallistOfImages,
                locValidationResult.value,
                couroutineScope,
                navController
            )
        }

    }

}





