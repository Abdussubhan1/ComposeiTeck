package com.example.itecktestingcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun mainScreen() {
    var devID by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    )
    {
        Column (modifier = Modifier.fillMaxSize()){
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(top = 18.dp)
                    .background(Color.Green)
                    .clip(shape = RoundedCornerShape(20.dp))
            ) {
                Row {
                    Image(
                        painterResource(id = R.drawable.icon),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(4.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Column() {
                        Text(
                            "خوش آمدید",
                            fontFamily = jameelNooriFont,
                            modifier = Modifier.padding(top = 5.dp),
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Text(
                            "Mr. ${Constants.name}",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                }
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(end = 18.dp, start = 18.dp), contentAlignment = Alignment.CenterEnd) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {


                    checkButton()

                    OutlinedTextField(
                        value = devID,
                        onValueChange = { devID = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 10.dp),
                        label = {
                            Text(
                                text = "ڈیوائس نمبر درج کریں",
                                modifier = Modifier.fillMaxWidth(),
                                fontFamily = jameelNooriFont,
                                textAlign = TextAlign.End
                            )
                        }
                    )
                }

            }

        }
    }

}