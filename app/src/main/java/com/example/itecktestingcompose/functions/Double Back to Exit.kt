package com.example.itecktestingcompose.functions

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HandleDoubleBackToExit() {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var backPressedOnce by remember { mutableStateOf(false) }


    BackHandler {
        if (backPressedOnce) {

            (context as? Activity)?.finish()

        } else {
            backPressedOnce = true
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
            coroutineScope.launch { delay(2000)
                backPressedOnce = false}

        }
    }
}