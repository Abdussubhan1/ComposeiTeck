package com.example.itecktestingcompose

import android.content.Context
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.DefaultTab.AlbumsTab.value
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun mainScreen(current: Context) {


    val couroutineScope = rememberCoroutineScope()
    var devID by remember { mutableStateOf("") }
    var validationResult by remember { mutableStateOf(DevValidationResult(false, false)) }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .background(colorResource(R.color.purple_200))
    ) {
        Card(
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.09f)
                .padding(top = 5.dp)
        )
        {
            Surface(color = Color.LightGray, modifier = Modifier.fillMaxSize()) {
                Row {
                    Image(
                        painter = painterResource(R.drawable.icon),
                        contentDescription = "",
                        modifier = Modifier.size(60.dp)
                    )

                    Column {
                        Text(
                            text = "خوش آمدید", fontFamily = jameelNooriFont,
                            modifier = Modifier.padding(start = 5.dp, top = 8.dp),
                            fontWeight = FontWeight.Bold, fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "Mr. ${Constants.name}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                    }
                }
            }


        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(

                imageVector = Icons.Rounded.Search,
                contentDescription = "Search Icon", tint = Color.Black,
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 10.dp)
                    .clickable {

                        if (devID.isEmpty() || devID.length < 7 || devID.length > 15) Toast.makeText(
                            current,
                            "Please enter device ID",
                            Toast.LENGTH_SHORT
                        ).show()
                        else
                            validationResult = DevValidationResult(false,true )
                            couroutineScope.launch {
                                validationResult= validateDev(devID)

                                if (validationResult.ifDeviceExist)
                                    Toast.makeText(
                                        current,
                                        "Device ID is valid",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else
                                    Toast.makeText(
                                        current,
                                        "Device ID is not valid",
                                        Toast.LENGTH_SHORT
                                    ).show()
                            }

                    }
            )
            OutlinedTextField(
                value = devID,
                onValueChange = { devID = it },
                label = {
                    Text(
                        "ڈیوائس نمبر درج کریں۔",
                        fontFamily = jameelNooriFont,
                        fontSize = 20.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                singleLine = true
            )

        }
        if(validationResult.isLoading) {
            loader()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    var context = LocalContext.current
    mainScreen(context)
}