package com.example.itecktestingcompose

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.itecktestingcompose.DataBase.AppDatabase
import com.example.itecktestingcompose.DataBase.DeviceSearchHistory
import kotlinx.coroutines.launch


@Composable
fun showHistory(current: Context) {


    var listItems by remember { mutableStateOf<List<DeviceSearchHistory>>(emptyList()) }
    val db = AppDatabase.AppDatabaseInstance.getDatabase(current)
    val dao = db.getHistory()
    val couroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        listItems = dao.getAllHistory()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .background(colorResource(R.color.white)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            Surface(color = Color.LightGray, modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.icon),
                        contentDescription = "",
                        modifier = Modifier.size(55.dp)
                    )

                    Text(
                        text = " History Tab",
                        fontWeight = FontWeight.Bold, fontSize = 20.sp
                    )

                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(listItems) { item ->
                    Text(
                        text = item.deviceNumber,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
        IconButton(onClick = {
            couroutineScope.launch {
                dao.deleteAll()
                listItems = emptyList()
            }
        }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.Red,
                modifier = Modifier.size(50.dp)
            )
        }


    }
}
