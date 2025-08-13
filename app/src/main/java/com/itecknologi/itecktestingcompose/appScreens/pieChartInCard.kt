package com.itecknologi.itecktestingcompose.appScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itecknologi.itecktestingcompose.apiFunctions.statisticsResponse

@Composable
fun CardPieChart(duration:Int,OnDurationChange: (Int) -> Unit, statistics: statisticsResponse?) {
    var selectDuration by remember(duration) {
        mutableStateOf(
            when(duration) {
                1 -> "Today"
                2 -> "7 Days"
                3 -> "1 Month"
                else -> "7 Days" // default
            }
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0XFF122333)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Statistics",
                    modifier = Modifier.padding(horizontal = 15.dp),
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.weight(1f)) {
                    DropdownField_forDurationStats(selectDuration, onOptionSelected = {duration->
                        when(duration){
                            "Today"-> OnDurationChange(1)
                            "7 Days"-> OnDurationChange(2)
                            "1 Month"-> OnDurationChange(3)
                        }
                        selectDuration=duration
                    })
                }
            }
            statistics?.let {
                key(it.hashCode()) { PieChartView(statistics = listOf(it)) }

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField_forDurationStats(
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val options = listOf("Today", "7 Days", "1 Month")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.padding(12.dp)
    ) {
        TextField(
            value = selectedOption,
            singleLine = true,
            onValueChange = {},
            readOnly = true,
            enabled = true, // allow selection
            modifier = Modifier
                .width(130.dp)
                .heightIn(min = 36.dp)  // smaller width
                .menuAnchor(),
            shape = RoundedCornerShape(30),
            textStyle = TextStyle(
                fontSize = 12.sp,  // smaller text
                color = Color(0xFF000000)
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color(0xFF000000),
                unfocusedTextColor = Color(0xFF000000),
                disabledTextColor = Color(0xFF000000),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,

                ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp) // smaller icon
                )
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onOptionSelected(selectionOption)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun CardPieChartPreview() {
    CardPieChart(1,OnDurationChange = {}, statistics = statisticsResponse(10,20,30,90))
}