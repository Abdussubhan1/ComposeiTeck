package com.example.itecktestingcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


val jameelNooriFont = FontFamily(Font(R.font.jameelnoori))

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            appNavigation(onButtonClick = { cnic, navController ->
                if (cnic.isEmpty()) Toast.makeText(
                    this@MainActivity,
                    "Please enter CNIC",
                    Toast.LENGTH_SHORT
                ).show()
                else {


                    lifecycleScope.launch {

                        if (validateCnic(cnic.replace("-", ""))) {
                            navController.navigate("mainscreen")
                            Toast.makeText(
                                this@MainActivity,
                                "Welcome ${Constants.name}", Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "User does not exist", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            })
        }
    }

    @Preview
    @Composable
    fun abc() {
        mainScreen()
    }
}

